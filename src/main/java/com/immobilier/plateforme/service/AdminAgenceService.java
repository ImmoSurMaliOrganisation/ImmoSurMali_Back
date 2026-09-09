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
    @Transactional(readOnly = true)
    public Page<User> searchAgences(UserStatut statut, String search, Pageable pageable) {
        // On transmet bien le statut au UserRepository
        return userRepository.findUsersWithFilters(Role.AGENCE_IMMOBILIERE, statut, search, pageable);
    }


    /**
     * Récupère une agence (utilisateur) par son ID pour la page de détails.
     *
     * @param id L'identifiant de l'agence
     * @return L'entité User correspondante
     */
    @Transactional(readOnly = true)
    public User getAgenceById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence introuvable avec l'ID : " + id));
    }
    /**
     * Rejette la demande d'inscription d'une agence avec un motif obligatoire.
     */
    @Transactional
    public void rejeterAgence(Long id, String motif) {
        User agence = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agence introuvable avec l'ID : " + id));

        // Vérification que l'utilisateur est bien une agence immobilière
        if (agence.getRole() != Role.AGENCE_IMMOBILIERE) {
            throw new IllegalArgumentException("Cet utilisateur n'est pas une agence immobilière.");
        }

        // Vérification de l'état actuel de la demande
        if (agence.getUserStatut() != UserStatut.EN_ATTENTE) {
            throw new IllegalArgumentException("Impossible de rejeter cette demande. Statut actuel : " + agence.getUserStatut());
        }

        // Mise à jour du statut, du motif et passage de la vérification à false
        agence.setUserStatut(UserStatut.REJETE);
        agence.setMotifRejet(motif);
        agence.setIsVerifier(true);

        userRepository.save(agence);

        // TODO: Déclencher l'envoi d'un email contenant le motif de rejet à l'agence
    }

}