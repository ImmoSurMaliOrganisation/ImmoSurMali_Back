package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.dto.RejetAgenceRequestDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> users = adminUserService.searchUsers(role, search, pageable);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<Void> suspendUser(@PathVariable Long id) {
        adminUserService.updateUserStatus(id, UserStatut.SUSPENDU);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        User updatedUser = adminUserService.updateUserStatus(id, UserStatut.ACTIF);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * API de rejet d'une demande d'inscription d'Agence avec motif obligatoire
     * URL : PATCH http://localhost:8081/api/v1/admin/users/agences/{id}/rejeter
     */
    @PatchMapping("/agences/{id}/rejeter")
    public ResponseEntity<?> rejeterAgence(
            @PathVariable("id") Long id,
            @Valid @RequestBody RejetAgenceRequestDTO dto
    ) {
        try {
            User agenceRejetee = adminUserService.rejeterAgence(id, dto);
            return ResponseEntity.ok(agenceRejetee);
        } catch (IllegalArgumentException e) {
            // Renvoie une erreur 400 Bad Request si les conditions métiers échouent
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
