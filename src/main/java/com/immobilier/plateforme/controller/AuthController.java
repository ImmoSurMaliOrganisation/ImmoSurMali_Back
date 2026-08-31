package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.LoginRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterAgenceRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterProprietaireRequestDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.service.AuthService;
import com.immobilier.plateforme.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * Endpoint d'authentification / connexion.
     * URL : POST http://localhost:8081/api/v1/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint d'inscription pour un client.
     * URL : POST http://localhost:8081/api/v1/auth/register/client
     */
    @PostMapping("/register/client")
    public ResponseEntity<AuthResponseDTO> registerClient(@RequestBody RegisterClientRequestDTO request) {
        AuthResponseDTO response = userService.registerClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint d'inscription pour un propriétaire particulier.
     * URL : POST http://localhost:8081/api/v1/auth/register/proprietaire
     */
    @PostMapping("/register/proprietaire")
    public ResponseEntity<AuthResponseDTO> registerProprietaire(@Valid @RequestBody RegisterProprietaireRequestDTO request) {
        AuthResponseDTO response = userService.registerProprietaire(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint d'inscription pour une agence immobilière (Tâche ISM-20)
     * URL : POST http://localhost:8081/api/v1/auth/register/agence
     * Consomme du multipart/form-data pour accepter les documents justificatifs
     */
    @PostMapping(
        value = "/register/agence", 
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> registerAgence(
        @RequestPart("data") @Valid RegisterAgenceRequestDTO dto,
        @RequestPart("rccmDocument") MultipartFile rccmDocument,
        @RequestPart(value = "nifDocument", required = false) MultipartFile nifDocument
    ) {
        try {
            System.out.println("le conrtoller est commencer ");
            User nouvelleAgence = authService.registerAgence(dto, rccmDocument, nifDocument);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAgence);
        } catch (IllegalArgumentException e) {
            // Capture et renvoie les erreurs métiers (Ex: formats ou documents obligatoires manquants)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
