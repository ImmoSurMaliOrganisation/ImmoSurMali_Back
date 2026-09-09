package com.immobilier.plateforme.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "appartements")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Appartement extends Bien {
    @Column(name = "nombre_chambres")
    private Integer nombreChambres;

    @Column(name = "est_meuble")
    private Boolean estMeuble = false;

    private Integer etage;

    private Boolean ascenseur = false;

    @Column(name = "charges_mensuelles")
    private Double chargesMensuelles;

    @Column(name = "surface_balcon")
    private Double surfaceBalcon;

    @Column(name = "parking_inclus")
    private Boolean parkingInclus = false;

    private Boolean piscine = false;
}
