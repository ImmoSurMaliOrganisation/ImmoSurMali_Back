package com.immobilier.plateforme.model.dto;

import com.immobilier.plateforme.enums.TypeTerrain;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTerrainRequestDTO {

    // Champs spécifiques exigés par le ticket
    @NotNull(message = "Le type de terrain est obligatoire")
    private TypeTerrain typeTerrain;

    @NotNull(message = "Le statut de viabilisation est obligatoire")
    private Boolean viabilise;

    @NotNull(message = "Le statut de clôture est obligatoire")
    private Boolean cloture;

    @NotNull(message = "La superficie totale est obligatoire")
    @Min(value = 1, message = "La superficie doit être supérieure à 0")
    private Double superficieTotale;

    @NotNull(message = "La mention du titre foncier est obligatoire")
    private Boolean titreFonce;

    // Méthodes/Champs requis par la structure héritée du Bien
    @NotNull(message = "Le titre du bien est obligatoire")
    private String titre;

    @NotNull(message = "Le prix du bien est obligatoire")
    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    private Double prix;
}
