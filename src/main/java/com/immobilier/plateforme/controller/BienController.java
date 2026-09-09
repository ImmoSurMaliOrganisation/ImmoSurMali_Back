package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.appartement.AppartementResponseDTO;
import com.immobilier.plateforme.model.dto.appartement.CreateAppartementRequestDTO;
import com.immobilier.plateforme.service.AppartementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/biens")
@RequiredArgsConstructor
public class BienController {
    private final AppartementService appartementService;

    /**
     * Endpoint d'enregistrement d'un appartement avec photos.
     * Consomme du multipart/form-data.
     */

    @PostMapping(value = "/appartements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AppartementResponseDTO> createAppartement(
            @RequestPart("data") @Valid CreateAppartementRequestDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {

        AppartementResponseDTO nouvelAppartement = appartementService.createAppartement(dto, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelAppartement);
    }


    /**
     * Récupère la liste paginée de tous les appartements.
     * GET /api/v1/biens/appartements?page=0&size=10&sortBy=createdAt&direction=DESC
     */
    @GetMapping("/appartements")
    public ResponseEntity<Page<AppartementResponseDTO>> getAllAppartements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(appartementService.getAllAppartements(pageable));
    }

    /**
     * Récupère les détails d'un appartement spécifique par son identifiant.
     * GET /api/v1/biens/appartements/{id}
     */
    @GetMapping("/appartements/{id}")
    public ResponseEntity<AppartementResponseDTO> getAppartementById(@PathVariable UUID id) {
        return ResponseEntity.ok(appartementService.getAppartementById(id));
    }
}
