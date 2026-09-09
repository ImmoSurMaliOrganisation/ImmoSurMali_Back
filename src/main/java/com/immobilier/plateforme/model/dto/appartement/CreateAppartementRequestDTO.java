package com.immobilier.plateforme.model.dto.appartement;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateAppartementRequestDTO {

    // --- CHAMPS COMMUNS (BIEN) ---
    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "Le prix doit être supérieur ou égal à 0")
    private Double prix;

    @Min(value = 0, message = "La surface habitable doit être positive")
    private Double surfaceHabitable;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le quartier est obligatoire")
    private String quartier;

    private String adresse;

    private Double latitude;

    private Double longitude;

    @NotNull(message = "L'ID du propriétaire est obligatoire")
    private Long proprietaireId;

    // --- CHAMPS SPÉCIFIQUES (APPARTEMENT) ---
    @Min(value = 0, message = "Le nombre de chambres doit être positif")
    private Integer nombreChambres;

    private Boolean estMeuble = false;

    private Integer etage;

    private Boolean ascenseur = false;

    private Boolean piscine = false;

    @Min(value = 0, message = "Les charges mensuelles doivent être positives")
    private Double chargesMensuelles;

    @Min(value = 0, message = "La surface du balcon doit être positive")
    private Double surfaceBalcon;

    private Boolean parkingInclus = false;

}