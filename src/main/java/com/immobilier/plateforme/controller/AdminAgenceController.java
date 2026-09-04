package com.immobilier.plateforme.controller;


import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.service.AdminAgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur REST d'administration dédié à la gestion des Agences Immobilières
 * (validation des comptes, examen des justificatifs RCCM & NIF, suspension/approbation).
 * Réservé exclusivement aux administrateurs.
 */
@RestController
@RequestMapping("/api/v1/admin/agences")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminAgenceController {

    private final AdminAgenceService adminAgenceService;
    /**
     * Récupère la liste paginée et filtrée des agences (ex: filtrage par statut
     * EN_ATTENTE pour examiner les nouvelles demandes de création de compte).
     *
     * @param statut Statut optionnel pour filtrer les agences (ex: EN_ATTENTE, ACTIF, SUSPENDU)
     * @param search Terme de recherche optionnel (nom de l'agence, email, etc.)
     * @param page   Numéro de la page demandée (par défaut 0)
     * @param size   Nombre d'éléments par page (par défaut 10)
     * @param sortBy Champ de tri des résultats (par défaut "id")
     * @param direction Direction du tri (ASC ou DESC, par défaut DESC)
     * @return Une page contenant la liste des agences correspondantes
     */
    @GetMapping
    public ResponseEntity<Page<User>> getAgences(
            @RequestParam(required = false) UserStatut statut,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        // Configuration du tri et de la pagination
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Appel du service pour récupérer les agences filtrées
        Page<User> agences = adminAgenceService.searchAgences(statut, search, pageable);
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getAgenceById(@PathVariable Long id) {
        User agence = adminAgenceService.getAgenceById(id);
        return ResponseEntity.ok(agence);
    }

    /**
            * Rejette la demande d'inscription d'une agence avec un motif obligatoire.
            * URL : PATCH /api/v1/admin/agences/{id}/rejeter
     */

    @PatchMapping("/{id}/rejeter")
    public ResponseEntity<Map<String, String>> rejeterAgence(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        String motif = requestBody.get("motif");
        if (motif == null || motif.trim().isEmpty()) {
            throw new IllegalArgumentException("Le motif de rejet est obligatoire.");
        }

        adminAgenceService.rejeterAgence(id, motif);

        // Renvoie un HTTP 200 OK avec un message clair
        Map<String, String> response = new HashMap<>();
        response.put("message", "La demande de l'agence a été rejetée avec succès.");
        return ResponseEntity.ok(response);
    }
}

