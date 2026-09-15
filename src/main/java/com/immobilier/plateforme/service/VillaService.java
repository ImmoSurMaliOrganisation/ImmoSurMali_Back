package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.villa.CreateVillaRequestDTO;
import com.immobilier.plateforme.model.dto.villa.VillaResponseDTO;
import com.immobilier.plateforme.model.entity.Villa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface VillaService {
    /**
     * Enregistre une nouvelle villa et traite ses fichiers médias associés.
     */
    VillaResponseDTO createVilla(CreateVillaRequestDTO dto, MultipartFile[] images);

    Page<VillaResponseDTO> getAllVillas(Pageable pageable);

    VillaResponseDTO getVillaById(UUID id);
}