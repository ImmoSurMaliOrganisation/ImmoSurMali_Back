package com.immobilier.plateforme.repository;

import com.immobilier.plateforme.model.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
    boolean existsByEmail(String email);
    boolean existsByRccm(String rccm);
}
