package com.immobilier.plateforme.service;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service gérant la logique métier administrative des Agences Immobilières
 * (consultation des demandes en attente, validation, rejet avec motif).
 */
@Service
@RequiredArgsConstructor
public class AdminAgenceService {

    private final UserRepository userRepository;
    /**
     * Récupère la liste paginée des agences en utilisant la requête de filtrage globale du UserRepository.
     * Force automatiquement le rôle à Role.AGENCE pour sécuriser le périmètre.
     *
     * @param statut Statut optionnel (non utilisé directement ici si non géré dans findUsersWithFilters, ou filtré via search)
     * @param search Terme de recherche optionnel (nom, email, téléphone...)
     * @param pageable Paramètres de pagination et de tri
     * @return Une page d'utilisateurs ayant le rôle AGENCE
     */
    public Page<User> searchAgences(UserStatut statut, String search, Pageable pageable) {
        // On transmet bien le statut au UserRepository
        return userRepository.findUsersWithFilters(Role.AGENCE_IMMOBILIERE, statut, search, pageable);
    }
}