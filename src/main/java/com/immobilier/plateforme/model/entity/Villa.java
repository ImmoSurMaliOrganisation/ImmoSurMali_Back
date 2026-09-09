package com.immobilier.plateforme.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entité enfant Villa héritant de la classe parente Bien.
 * Version alternative sans @SuperBuilder.
 */
@Entity
@Table(name = "villas")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Villa extends Bien {

    @Column(name = "surface_terrain", nullable = false)
    private Double surfaceTerrain;

    @Column(name = "nombre_facades")
    private Integer nombreFacades;

    @Column(name = "jardin")
    private Boolean jardin;

    @Column(name = "surface_jardin")
    private Double surfaceJardin;

    @Column(name = "piscine")
    private Boolean piscine;

    @Column(name = "garage")
    private Boolean garage;
}
