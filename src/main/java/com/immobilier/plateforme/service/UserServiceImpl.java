package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService; // On injecte AuthService qui sait déjà générer les tokens !

    @Override
    public AuthResponseDTO registerClient(RegisterClientRequestDTO request) {
        // 1. Vérifier l'unicité de l'email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // 2. Créer l'entité User
        User client = User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .telephone(request.getTelephone())
                .role(com.immobilier.plateforme.enums.Role.CLIENT) 
                .userStatus(com.immobilier.plateforme.enums.UserStatut.ACTIF) 
                .isVerifier(true)
                .dateCreation(LocalDateTime.now())
                .build();

        // 3. Sauvegarder
        userRepository.save(client);

        // 4. Utiliser la logique existante de connexion pour générer et retourner le token JWT complet
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(request.getEmail());
        loginRequest.setMotDePasse(request.getMotDePasse());
        
        return authService.login(loginRequest);
    }
}
