package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterProprietaireRequestDTO; // À importer après création du DTO
import com.immobilier.plateforme.service.AuthService;
import com.immobilier.plateforme.service.UserService; // Import du service utilisateur
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService; // Injection de UserService pour la création de compte

    /**
     * Endpoint d'authentification / connexion.
     * URL : POST http://localhost:8080/api/v1/auth/login
     *
     * @param request Objet DTO contenant l'email et le mot de passe
     * @return ResponseEntity contenant le token JWT, l'email et le rôle
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    /**
     * Endpoint d'inscription pour un client.
     * URL : POST http://localhost:8080/api/v1/auth/register/client
     *
     * @param request Objet DTO contenant les informations du propriétaire
     * @return ResponseEntity contenant le token JWT avec un statut 201 Created
     */
    @PostMapping("/register/client")
    public ResponseEntity<AuthResponseDTO> registerClient(@RequestBody RegisterClientRequestDTO request) {
        AuthResponseDTO response = userService.registerClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    /**
     * Endpoint d'inscription pour un propriétaire particulier.
     * URL : POST http://localhost:8080/api/v1/auth/register/proprietaire
     *
     * @param request Objet DTO contenant les informations du propriétaire
     * @return ResponseEntity contenant le token JWT avec un statut 201 Created
     */
    @PostMapping("/register/proprietaire")
    public ResponseEntity<AuthResponseDTO> registerProprietaire(@Valid @RequestBody RegisterProprietaireRequestDTO request) {
        AuthResponseDTO response = userService.registerProprietaire(request);
        // Critère d'acceptation : retourne un statut 201 Created
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
