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

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UserSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        String defaultPassword = passwordEncoder.encode("!Test1234");
        // 1. Super Admin principal
        String adminEmail = "admin@immo.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = User.builder()
                    .nom("Super Admin")
                    .email(adminEmail)
                    .motDePasse(defaultPassword)
                    .telephone("+22370000000")
                    .role(Role.ADMIN)
                    .userStatut(UserStatut.ACTIF)
                    .isVerifier(true)
                    .build();

            userRepository.save(admin);
            log.info("✅ [SEEDER] Compte Super Admin créé : {} / !Test1234", adminEmail);
        }

        // 2. Génération automatique d'utilisateurs de test
        List<User> dummyUsers = new ArrayList<>();


        // Clients de test
        for (int i = 1; i <= 150; i++) {
            String email = "client" + i + "@test.com";
            if (!userRepository.existsByEmail(email)) {
                dummyUsers.add(User.builder()
                        .nom("Client Test " + i)
                        .email(email)
                        .motDePasse(defaultPassword)
                        .telephone("+223750000" + (10 + i))
                        .role(Role.CLIENT)
                        .userStatut(i % 5 == 0 ? UserStatut.SUSPENDU : UserStatut.ACTIF)
                        .isVerifier(true)
                        .build());
            }
        }

        // Propriétaires de test
        for (int i = 1; i <= 42; i++) {
            String email = "proprio" + i + "@test.com";
            if (!userRepository.existsByEmail(email)) {
                dummyUsers.add(User.builder()
                        .nom("Propriétaire " + i)
                        .email(email)
                        .motDePasse(defaultPassword)
                        .telephone("+223660000" + (10 + i))
                        .role(Role.PROPRIETAIRE_PART)
                        .userStatut(UserStatut.ACTIF)
                        .isVerifier(true)
                        .build());
            }
        }

        // Agences de test
        for (int i = 1; i <= 12; i++) {
            String email = "agence" + i + "@immo.com";
            if (!userRepository.existsByEmail(email)) {
                dummyUsers.add(User.builder()
                        .nom("Agence Immobilière " + i)
                        .email(email)
                        .motDePasse(defaultPassword)
                        .telephone("+223200000" + (10 + i))
                        .role(Role.AGENCE_IMMOBILIERE)
                        .userStatut(UserStatut.ACTIF)
                        .isVerifier(true)
                        .build());
            }
        }

        if (!dummyUsers.isEmpty()) {
            userRepository.saveAll(dummyUsers);
            log.info("🚀 [SEEDER] {} utilisateurs fictifs ont été ajoutés à la BDD.", dummyUsers.size());
        } else {
            log.info("ℹ️ [SEEDER] Tous les utilisateurs fictifs existent déjà.");
        }
    }}