package com.immobilier.plateforme.model.dto.villa;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

/**
 * DTO de requête contenant les attributs communs de Bien et 
 * les attributs spécifiques de la villa pour l'API multipart/form-data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVillaRequestDTO {

    // --- Attributs communs de la classe parente Bien ---
    @NotBlank(message = "Le titre du bien est obligatoire")
    private String titre;

    @NotBlank(message = "La description du bien est obligatoire")
    private String description;

    @NotNull(message = "Le prix du bien est obligatoire")
    @Positive(message = "Le prix doit être strictement supérieur à 0")
    private Double prix;

    @NotNull(message = "La surface habitable est obligatoire")
    @Positive(message = "La surface habitable doit être supérieure à 0")
    private Double surfaceHabitable;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le quartier est obligatoire")
    private String quartier;

    @NotBlank(message = "L'adresse du bien est obligatoire")
    private String adresse;

    private Double latitude;

    private Double longitude;

    @NotNull(message = "L'ID du propriétaire est obligatoire")
    private Long proprietaireId;

    // --- Attributs spécifiques requis pour l'entité Villa ---
    @NotNull(message = "La surface du terrain est obligatoire")
    @Positive(message = "La surface du terrain doit être supérieure à 0")
    private Double surfaceTerrain;

    @Min(value = 0, message = "Le nombre de chambres ne peut pas être négatif")
    private Integer nombreChambres;

    @NotNull(message = "Le nombre de façades est obligatoire")
    @Min(value = 1, message = "Le nombre de façades doit être d'au moins 1")
    private Integer nombreFacades;

    @NotNull(message = "L'indication concernant la présence d'un jardin est obligatoire")
    private Boolean jardin;

    @PositiveOrZero(message = "La surface du jardin doit être supérieure à 0")
    private Double surfaceJardin;

    @NotNull(message = "L'indication concernant la présence d'une piscine est obligatoire")
    private Boolean piscine;

    @NotNull(message = "L'indication concernant la présence d'un garage est obligatoire")
    private Boolean garage;
}