package com.immobilier.plateforme.model.dto.terrain;

import com.immobilier.plateforme.enums.TypeTerrain;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTerrainRequestDTO {

    // --- Champs communs (hérités du Bien) ---
    @NotNull(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être supérieur à 0")
    private Double prix;

    private Double surfaceHabitable;

    @NotNull(message = "La ville est obligatoire")
    private String ville;

    @NotNull(message = "Le quartier est obligatoire")
    private String quartier;

    private String adresse;
    private Double latitude;
    private Double longitude;

    // --- Champs spécifiques au Terrain ---
    @NotNull(message = "Le type de terrain est obligatoire")
    private TypeTerrain typeTerrain;

    @NotNull(message = "La superficie totale est obligatoire")
    @Positive(message = "La superficie totale doit être supérieure à 0")
    private Double superficieTotale;

    private Integer nombreFacades = 1;
    private String zonage;
    private Boolean viabilise = false;
    private Boolean cloture = false;
    private Boolean titreFoncier = true;
    private Boolean eau = false;
    private Boolean electricite = false;
    private Boolean accesGoudronne = false;
    private Boolean assainissement = false;
}