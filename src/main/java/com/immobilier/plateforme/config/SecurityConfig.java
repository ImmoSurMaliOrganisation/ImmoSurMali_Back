package com.immobilier.plateforme.config; 



import com.immobilier.plateforme.config.JwtAuthenticationFilter;
import org.springframework.security.config.Customizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Bean pour le hachage sécurisé des mots de passe.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                // FORCER SPRING SECURITY À NE PAS CRÉER NI UTILISER DE SESSIONS HTTP
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // 1. Documentation OpenAPI / Swagger publique
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 2. Auth publique (login, register)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // 3. Endpoints Admin (Accès STRICTEMENT réservé au rôle ADMIN)
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // 4. Tout le reste de l'API requiert une authentification
                        .anyRequest().authenticated()
                )
                // Filtre JWT
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}