package com.immobilier.plateforme.model.dto.Villa;

import jakarta.validation.constraints.*;
import lombok.*;

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

    @NotBlank(message = "L'adresse du bien est obligatoire")
    private String adresse;

    // --- Attributs spécifiques requis pour l'entité Villa ---
    @NotNull(message = "La surface du terrain est obligatoire")
    @Positive(message = "La surface du terrain doit être supérieure à 0")
    private Double surfaceTerrain;

    @NotNull(message = "Le nombre de façades est obligatoire")
    @Min(value = 1, message = "Le nombre de façades doit être d'au moins 1")
    private Integer nombreFacades;

    @NotNull(message = "L'indication concernant la présence d'un jardin est obligatoire")
    private Boolean jardin;

    @Positive(message = "La surface du jardin doit être supérieure à 0")
    private Double surfaceJardin; // Optionnel (sans @NotNull) comme demandé dans la consigne

    @NotNull(message = "L'indication concernant la présence d'une piscine est obligatoire")
    private Boolean piscine;

    @NotNull(message = "L'indication concernant la présence d'un garage est obligatoire")
    private Boolean garage;
}


