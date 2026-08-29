package com.immobilier.plateforme.repository;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.model.dto.UserAdminResponseDTO;
import com.immobilier.plateforme.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Recherche un utilisateur par son adresse email.
     * Utilise Optional pour éviter les NullPointerException.
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà dans la base de données.
     * Utile pour la validation lors de la création/inscription d'un compte.
     */
    boolean existsByEmail(String email);

    /**
     * Récupère tous les utilisateurs pour l'administration avec filtrage, recherche et pagination.
     */
    @Query("SELECT u FROM User u WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "u.telephone LIKE CONCAT('%', :search, '%')) " +
            "ORDER BY u.dateCreation DESC")
    Page<User> findAllUsersForAdmin(@Param("search") String search, Pageable pageable);

}