package com.immobilier.plateforme.model.entity;

import com.immobilier.plateforme.enums.StatutBien;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "biens")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class Bien {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double prix;

    @Column(name = "surface_habitable")
    private Double surfaceHabitable;

    // --- ENUM STATUT ---
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutBien statut = StatutBien.DISPONIBLE;

    // --- CHAMPS DE LOCALISATION ORDONNÉS ---
    @Column(nullable = false)
    private String ville;               // 1. Ville / Commune

    @Column(nullable = false)
    private String quartier;            // 2. Quartier / Zone

    private String adresse;             // 3. Adresse précise / Rue

    private Double latitude;            // 4. Coordonnée GPS Carte

    private Double longitude;           // 5. Coordonnée GPS Carte

    // --- MÉTADONNÉES ET RELATIONS ---
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proprietaire_id", nullable = false)
    private User proprietaire;

    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Media> medias = new ArrayList<>();



}
