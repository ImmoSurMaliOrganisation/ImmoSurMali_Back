package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.dto.UserAdminResponseDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    /**
     * Récupère la liste paginée et filtrée des utilisateurs du système.
     *
     * @param role      Filtre optionnel par rôle (ex: AGENCE, PROPRIETAIRE_PART)
     * @param search    Terme de recherche optionnel (nom, email, etc.)
     * @param page      Numéro de la page demandée (par défaut 0)
     * @param size      Nombre d'éléments par page (par défaut 10)
     * @param sortBy    Champ de tri des résultats (par défaut "id")
     * @param direction Direction du tri (ASC ou DESC, par défaut ASC)
     * @return Une page d'utilisateurs correspondants
     */
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) UserStatut statut,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        // Construction de l'objet de tri et de pagination
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Appel du service pour récupérer les utilisateurs filtrés
        Page<User> users = adminUserService.searchUsers(role,statut, search, pageable);
        return ResponseEntity.ok(users);
    }

    /**
     * Suspend le compte d'un utilisateur spécifique.
     *
     * @param id L'identifiant unique de l'utilisateur à suspendre
     * @return Réponse vide avec le code HTTP 204 No Content en cas de succès
     */
    @PatchMapping("/{id}/suspend")
    public ResponseEntity<Void> suspendUser(@PathVariable Long id) {
        adminUserService.updateUserStatus(id, UserStatut.SUSPENDU);
        return ResponseEntity.noContent().build();
    }

    /**
     * Active ou réactive le compte d'un utilisateur spécifique.
     *
     * @param id L'identifiant unique de l'utilisateur à activer
     * @return L'entité utilisateur mise à jour avec le statut ACTIF (Code HTTP 200 OK)
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        User updatedUser = adminUserService.updateUserStatus(id, UserStatut.ACTIF);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Supprime définitivement un utilisateur de la plateforme.
     *
     * @param id L'identifiant unique de l'utilisateur à supprimer
     * @return Réponse vide avec le code HTTP 204 No Content en cas de succès
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}