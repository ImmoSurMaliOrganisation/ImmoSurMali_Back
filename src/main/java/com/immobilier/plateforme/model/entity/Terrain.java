package com.immobilier.plateforme.model.entity;

import com.immobilier.plateforme.enums.TypeTerrain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "terrains")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Terrain extends Bien {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_terrain", nullable = false, length = 50)
    private TypeTerrain typeTerrain;

    @Column(name = "superficie_totale", nullable = false)
    private Double superficieTotale;

    @Column(name = "nombre_facades")
    private Integer nombreFacades = 1;

    @Column(name = "zonage", length = 100)
    private String zonage;

    @Column(name = "viabilise")
    private Boolean viabilise = false;

    @Column(name = "cloture")
    private Boolean cloture = false;

    @Column(name = "titre_foncier")
    private Boolean titreFoncier = true;

    @Column(name = "eau")
    private Boolean eau = false;

    @Column(name = "electricite")
    private Boolean electricite = false;

    @Column(name = "acces_goudronne")
    private Boolean accesGoudronne = false;

    @Column(name = "assainissement")
    private Boolean assainissement = false;
}