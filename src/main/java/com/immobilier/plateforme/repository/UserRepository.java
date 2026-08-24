package com.immobilier.plateforme.repository;
import com.immobilier.plateforme.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}