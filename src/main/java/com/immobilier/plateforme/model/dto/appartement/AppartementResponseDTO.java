package com.immobilier.plateforme.model.dto.appartement;

import com.immobilier.plateforme.enums.StatutBien;
import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AppartementResponseDTO {

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
    private Long proprietaireId;
    private LocalDateTime createdAt;

    // Attributs spécifiques Appartement
    private Integer nombreChambres;
    private Boolean estMeuble;
    private Integer etage;
    private Boolean ascenseur;
    private Boolean piscine;
    private Double chargesMensuelles;
    private Double surfaceBalcon;
    private Boolean parkingInclus;

    // Médias retournés
    private List<MediaResponseDTO> medias;
}