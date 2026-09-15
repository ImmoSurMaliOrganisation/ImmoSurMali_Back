package com.immobilier.plateforme.model.dto.villa;

import com.immobilier.plateforme.enums.StatutBien;
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
public class VillaResponseDTO {

    // Champs hérités du Bien parent
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

    // Champs spécifiques à la Villa
    private Double surfaceTerrain;
    private Integer nombreChambres;
    private Integer nombreFacades;
    private Boolean jardin;
    private Double surfaceJardin;
    private Boolean piscine;
    private Boolean garage;

    // Fichiers multimédias associés
    private List<MediaResponseDTO> medias;
}