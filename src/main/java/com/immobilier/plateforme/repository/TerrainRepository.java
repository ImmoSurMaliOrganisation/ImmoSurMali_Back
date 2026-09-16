package com.immobilier.plateforme.repository;

import com.immobilier.plateforme.model.entity.Terrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TerrainRepository extends JpaRepository<Terrain, UUID> {

}