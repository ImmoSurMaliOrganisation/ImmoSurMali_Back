package com.immobilier.plateforme.repository;

import com.immobilier.plateforme.model.entity.Villa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillaRepository extends JpaRepository<Villa, Long> {
}
