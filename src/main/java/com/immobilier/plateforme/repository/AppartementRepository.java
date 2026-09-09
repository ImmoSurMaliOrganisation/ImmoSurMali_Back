package com.immobilier.plateforme.repository;

import com.immobilier.plateforme.model.entity.Appartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppartementRepository extends JpaRepository<Appartement, UUID> {

}
