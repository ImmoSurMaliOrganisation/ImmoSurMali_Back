package com.immobilier.plateforme.model.entity;

import com.immobilier.plateforme.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "agences")
@Getter
@Setter
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomAgence;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String motDePasse;
    
    @Column(nullable = false)
    private String telephone;
    
    @Column(nullable = false)
    private String adresse;

    @Column(unique = true, nullable = false)
    private String rccm;

    @Column(nullable = false)
    private String rccmDocumentUrl; // Lien vers le document obligatoire

    private String nif; // Optionnel

    private String nifDocumentUrl; // Optionnel

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.AGENCE_IMMOBILIERE; // Aligné sur votre énumération

    @Column(nullable = false)
    private String userStatus = "EN_ATTENTE";

    @Column(nullable = false)
    private boolean isVerifier = false;
}
