package com.immobilier.plateforme.config;

import com.immobilier.plateforme.config.JwtAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Bean pour le hachage sécurisé des mots de passe (utilisé par AuthService).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())// 1. Activer le CORS
                // 2. Désactiver le CSRF (car API Stateless avec JWT)
                .csrf(AbstractHttpConfigurer::disable)

                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // Désactive complètement la protection iframe pour le développement
                )
                // 3. Forcer le mode Stateless (pas de session HTTP)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Autoriser les requêtes Preflight OPTIONS du navigateur
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Autoriser l'accès public au dossier des uploads
                        .requestMatchers("/uploads/**").permitAll()

                        // Routes publiques (Auth, tests et Swagger)
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/test",
                                "/api/test-db",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Endpoints Admin protégés par rôle
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // Tout le reste nécessite d'être authentifié
                        .anyRequest().authenticated()
                )
                // 4. RÉINTÉGRER LE FILTRE JWT (Indispensable pour lire le token)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}