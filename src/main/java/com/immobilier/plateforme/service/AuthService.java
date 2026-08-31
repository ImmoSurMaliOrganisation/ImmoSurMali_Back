package com.immobilier.plateforme.service;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterAgenceRequestDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService; 
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Authentifie un utilisateur et génère son jeton JWT.
     */
    public AuthResponseDTO login(LoginRequestDTO request) {
        // 1. Recherche de l'utilisateur en base de données par son email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        // 2. Vérification du mot de passe
        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // 3. Génération du token JWT
        String token = jwtService.generateToken(user);

        // 4. Construction et renvoi de la réponse
        return AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    /**
     * Logique métier complète pour inscrire une Agence Immobilière (Tâche ISM-20)
     */
    public User registerAgence(RegisterAgenceRequestDTO dto, MultipartFile rccmDocument, MultipartFile nifDocument) {

        // 1. Vérifier l'unicité de l'email
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà enregistré.");
        }

        // 2. Vérifier l'unicité du numéro RCCM
        if (dto.getRccm() != null && userRepository.existsByRccm(dto.getRccm())) {
            throw new IllegalArgumentException("Ce numéro RCCM est déjà enregistré.");
        }

        // 3. Valider que le document RCCM est bien présent (obligatoire)
        if (rccmDocument == null || rccmDocument.isEmpty()) {
            throw new IllegalArgumentException("Le document justificatif RCCM est obligatoire.");
        }
        // 4. Vérifier l'unicité du NIF (s'il est fourni)
        if (dto.getNif() != null && !dto.getNif().isBlank() && userRepository.existsByNif(dto.getNif())) {
            throw new IllegalArgumentException("Ce numéro NIF est déjà enregistré.");
        }

        // 5. Enregistrer les fichiers physiques et récupérer leurs chemins/URLs
        String rccmDocUrl = fileStorageService.storeFile(rccmDocument);
        String nifDocUrl = (nifDocument != null && !nifDocument.isEmpty()) ? fileStorageService.storeFile(nifDocument) : null;

        // 6. Construire l'entité User unifiée avec le rôle AGENCE
        User agenceUser = User.builder()
                .nomAgence(dto.getNomAgence()) // Nom du représentant ou de contact principal
                .email(dto.getEmail())
                .motDePasse(passwordEncoder.encode(dto.getMotDePasse()))
                .telephone(dto.getTelephone())
                .role(Role.AGENCE_IMMOBILIERE)
                .userStatut(UserStatut.EN_ATTENTE) // En attente de validation administrative
                .isVerifier(false)
                .adresse(dto.getAdresse())
                .rccm(dto.getRccm())
                .rccmDocumentUrl(rccmDocUrl)
                .nif(dto.getNif())
                .nifDocumentUrl(nifDocUrl)
                .build();

        // 7. Sauvegarder en base de données
        return userRepository.save(agenceUser);
    }
}
