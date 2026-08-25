package com.immobilier.plateforme.config;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@immo.com";

        // Vérification d'existence pour éviter les doublons au redémarrage
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .nom("Super Admin")
                    .email(adminEmail)
                    .motDePasse(passwordEncoder.encode("!Test1234"))
                    .telephone("+22370000000")
                    .role(Role.ADMIN)
                    .userStatus(UserStatut.ACTIF)
                    .isVerifier(true)
                    .build();

            userRepository.save(admin);
            log.info("✅ [SEEDER] Compte Super Admin créé : {} / !Test1234", adminEmail);
        } else {
            log.info("ℹ️ [SEEDER] Le compte Super Admin ({}) existe déjà.", adminEmail);
        }
    }
}