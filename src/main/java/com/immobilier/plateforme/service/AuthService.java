package com.immobilier.plateforme.service;


import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService  jwtService;
/**
 * Authentifie un utilisateur et génère son jeton JWT.
 */
public AuthResponseDTO login(LoginRequestDTO request){
    // 1. Recherche de l'utilisateur en base de données par son email
    User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("Email ou mot de passe incorrect"));

    // 2. Vérification du mot de passe
    // On compare le mot de passe en clair (envoyé par le client)
    // avec le mot de passe haché (stocké en base)
    if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
        throw new RuntimeException("Email ou mot de passe incorrect");
    }

    // 3. Génération du token JWT
    // Si les identifiants sont bons, on fabrique le jeton
    String token = jwtService.generateToken(user);

    // 4. Construction et renvoi de la réponse
    return AuthResponseDTO.builder()
            .token(token)
            .tokenType("Bearer")
            .email(user.getEmail())
            .role(user.getRole())
            .build();
}
}
