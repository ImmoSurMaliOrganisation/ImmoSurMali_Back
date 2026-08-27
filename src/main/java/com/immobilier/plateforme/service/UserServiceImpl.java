package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterProprietaireRequestDTO;
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
    private final AuthService authService; 

    @Override
    public AuthResponseDTO registerClient(RegisterClientRequestDTO request) {
        // 1. Vérifier l'unicité de l'email (Erreur 400 via ValidationException)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new com.immobilier.plateforme.exception.ValidationException("Cet email est déjà utilisé");
        }

        // 2. Création de l'entité User
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

    // =========================================================================
    // AJOUT DE LA MÉTHODE POUR LE PROPRIÉTAIRE PARTICULIER
    // =========================================================================
    @Override
    public AuthResponseDTO registerProprietaire(RegisterProprietaireRequestDTO request) {
        // 1. Vérifier l'unicité de l'email (Critère d'acceptation 400 Bad Request)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new com.immobilier.plateforme.exception.ValidationException("Cet email est déjà utilisé"); 
        }

        // 2. Créer l'entité User pour le Propriétaire avec le Builder
        User proprietaire = User.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .motDePasse(passwordEncoder.encode(request.getMotDePasse()))
                .telephone(request.getTelephone())
                .role(com.immobilier.plateforme.enums.Role.PROPRIETAIRE_PART) // Assigne automatiquement le rôle demandé
                .userStatus(com.immobilier.plateforme.enums.UserStatut.ACTIF) // Statut ACTIF automatique
                .isVerifier(true)
                .dateCreation(LocalDateTime.now())
                .build();

        // 3. Sauvegarder dans la base de données
        userRepository.save(proprietaire);

        // 4. Générer et retourner le jeton JWT complet en simulant la connexion (comme pour le client)
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setEmail(request.getEmail());
        loginRequest.setMotDePasse(request.getMotDePasse());
        
        return authService.login(loginRequest);
    }
}
