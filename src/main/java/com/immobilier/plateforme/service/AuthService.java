package com.immobilier.plateforme.service;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterAgenceRequestDTO;
import com.immobilier.plateforme.model.entity.Agence;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.AgenceRepository;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AgenceRepository agenceRepository; 
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
    public Agence registerAgence(RegisterAgenceRequestDTO dto, MultipartFile rccmDocument, MultipartFile nifDocument) {
        
        // 1. Validation de l'obligation stricte du fichier RCCM
        if (rccmDocument == null || rccmDocument.isEmpty()) {
            throw new IllegalArgumentException("Le document justificatif RCCM est obligatoire");
        }

        // 2. Vérification des doublons Email ou RCCM
        if (agenceRepository.existsByEmail(dto.getEmail()) || agenceRepository.existsByRccm(dto.getRccm())) {
            throw new IllegalArgumentException("Cet email ou RCCM est déjà enregistré");
        }

        // 3. Téléversement des documents justificatifs via le FileStorageService
        String rccmUrl = fileStorageService.storeFile(rccmDocument);
        String nifUrl = fileStorageService.storeFile(nifDocument);

        // 4. Instanciation et mapping de l'entité Agence
        Agence agence = new Agence();
        agence.setNomAgence(dto.getNomAgence());
        agence.setEmail(dto.getEmail());
        agence.setTelephone(dto.getTelephone());
        agence.setAdresse(dto.getAdresse());
        agence.setRccm(dto.getRccm());
        agence.setRccmDocumentUrl(rccmUrl);
        
        // Données Optionnelles NIF
        agence.setNif(dto.getNif());
        agence.setNifDocumentUrl(nifUrl);

        // 5. Hachage sécurisé du mot de passe (utilise l'instance PasswordEncoder globale)
        agence.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));

        // 6. Assignation des statuts requis par les critères d'acceptation
        agence.setRole(Role.AGENCE_IMMOBILIERE);
        agence.setUserStatus("EN_ATTENTE");
        agence.setVerifier(false);

        // 7. Persistance finale dans PostgreSQL
        return agenceRepository.save(agence);
    }
}
