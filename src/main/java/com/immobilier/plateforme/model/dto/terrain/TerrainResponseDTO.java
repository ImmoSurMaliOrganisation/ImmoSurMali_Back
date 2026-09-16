package com.immobilier.plateforme.model.dto.terrain;

import com.immobilier.plateforme.enums.StatutBien;
import com.immobilier.plateforme.enums.TypeTerrain;
import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerrainResponseDTO {

    // --- Champs communs (hérités de Bien) ---
    private UUID id;
    private String titre;
    private String description;
    private Double prix;
    private Double surfaceHabitable;
    private StatutBien statut;
    private String ville;
    private String quartier;
    private String adresse;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
    private List<MediaResponseDTO> medias;

    // --- Champs spécifiques au Terrain ---
    private TypeTerrain typeTerrain;
    private Double superficieTotale;
    private Integer nombreFacades;
    private String zonage;
    private Boolean viabilise;
    private Boolean cloture;
    private Boolean titreFoncier;
    private Boolean eau;
    private Boolean electricite;
    private Boolean accesGoudronne;
    private Boolean assainissement;
}