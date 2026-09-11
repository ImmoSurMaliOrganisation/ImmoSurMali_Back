package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.Villa.CreateVillaRequestDTO;
import com.immobilier.plateforme.model.entity.Villa;
import com.immobilier.plateforme.service.VillaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Contrôleur REST exposant l'API de création des villas au format Multipart.
 */
@RestController
@RequestMapping("/api/v1/biens/villas")
@RequiredArgsConstructor
@Tag(name = "Villas", description = "Gestion des villas")
public class VillaController {

    private final VillaService villaService;

    /**
     * Endpoint de création d'une villa avec ses images.
     * Consomme du multipart/form-data contenant un bloc JSON ("data") et un tableau de fichiers ("images").
     *
     * @param dto    Les caractéristiques de la villa (désérialisées depuis le JSON et validées).
     * @param images Les fichiers photos optionnels de la villa.
     * @return La villa créée avec le code HTTP 201 Created.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Créer une nouvelle villa avec des images",
        description = "Enregistre une villa en associant ses caractéristiques (JSON) et ses fichiers binaires de photos."
    )
    public ResponseEntity<Villa> createVilla(
            @RequestPart("data") @Valid CreateVillaRequestDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        
        Villa createdVilla = villaService.createVilla(dto, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVilla);
    }
}
