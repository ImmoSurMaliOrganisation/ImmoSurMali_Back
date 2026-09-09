package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.Villa.CreateVillaRequestDTO;
import com.immobilier.plateforme.model.entity.Villa;
import com.immobilier.plateforme.repository.VillaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VillaServiceImpl implements VillaService {

    private final VillaRepository villaRepository;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public Villa createVilla(CreateVillaRequestDTO dto, MultipartFile[] images) {
        
        // Instanciation classique pour éviter les conflits de @SuperBuilder avec la classe Bien
        Villa villa = new Villa();
        
        // Attributs communs de la classe parente Bien
        villa.setTitre(dto.getTitre());
        villa.setDescription(dto.getDescription());
        villa.setPrix(dto.getPrix());
        villa.setAdresse(dto.getAdresse());
        
        // Attributs spécifiques de l'entité Villa
        villa.setSurfaceTerrain(dto.getSurfaceTerrain());
        villa.setNombreFacades(dto.getNombreFacades() != null ? dto.getNombreFacades() : 4);
        villa.setJardin(dto.getJardin());
        villa.setSurfaceJardin(dto.getSurfaceJardin());
        villa.setPiscine(dto.getPiscine());
        villa.setGarage(dto.getGarage());

        // Sauvegarde dans la base de données via Hibernate (gère le polymorphisme JOINED)
        Villa savedVilla = villaRepository.save(villa);

        // Traitement de vos images avec votre service FileStorageService
        if (images != null && images.length > 0) {
            // fileStorageService.saveMultipleFiles(images, savedVilla.getId());
        }

        return savedVilla;
    }
}
