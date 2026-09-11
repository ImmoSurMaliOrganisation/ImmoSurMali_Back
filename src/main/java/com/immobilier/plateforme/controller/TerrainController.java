package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.CreateTerrainRequestDTO;
import com.immobilier.plateforme.model.entity.Terrain;
import com.immobilier.plateforme.service.TerrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/biens/terrains")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TerrainController {

    private final TerrainService terrainService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED) 
    public ResponseEntity<Terrain> createTerrain(
            @RequestPart("data") @Valid CreateTerrainRequestDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        
        Terrain createdTerrain = terrainService.createTerrain(dto, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTerrain);
    }
}

