package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.Villa.CreateVillaRequestDTO;
import com.immobilier.plateforme.model.entity.Villa;
import org.springframework.web.multipart.MultipartFile;

public interface VillaService {
    /**
     * Enregistre une nouvelle villa et traite ses fichiers médias associés.
     */
    Villa createVilla(CreateVillaRequestDTO dto, MultipartFile[] images);
}
