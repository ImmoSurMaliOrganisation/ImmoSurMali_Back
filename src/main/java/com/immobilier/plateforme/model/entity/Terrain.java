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

    @Column(name = "viabilise")
    private Boolean viabilise = false;

    @Column(name = "cloture")
    private Boolean cloture = false;

    @Column(name = "superficie_totale", nullable = false)
    private Double superficieTotale;

    @Column(name = "titre_fonce")
    private Boolean titreFonce = true;
}
