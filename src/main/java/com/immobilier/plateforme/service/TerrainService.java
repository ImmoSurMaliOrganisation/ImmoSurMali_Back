package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.CreateTerrainRequestDTO;
import com.immobilier.plateforme.model.entity.Terrain;
import com.immobilier.plateforme.repository.TerrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;

    @Transactional
    public Terrain createTerrain(CreateTerrainRequestDTO dto, MultipartFile[] images) {
        Terrain terrain = new Terrain();
         // Mapping des champs spécifiques
        terrain.setTypeTerrain(dto.getTypeTerrain());
        terrain.setViabilise(dto.getViabilise());
        terrain.setCloture(dto.getCloture());
        terrain.setSuperficieTotale(dto.getSuperficieTotale());
        terrain.setTitreFonce(dto.getTitreFonce());


           // Mapping des champs communs de la classe parente Bien
        terrain.setTitre(dto.getTitre());
        terrain.setPrix(dto.getPrix());
        
        
        // Sauvegarde de l'entité (déclenche l'insertion JOINED en cascade)
        Terrain savedTerrain = terrainRepository.save(terrain);

 // Logique de traitement et stockage des fichiers (photos, plans cadastres)
        if (images != null && images.length > 0) {
            // List<String> fileUrls = fileStorageService.uploadFiles(images, "terrains/" + savedTerrain.getId());
            // savedTerrain.setMedias(fileUrls); // À adapter selon votre implémentation des médias sur Bien
        }


        return terrainRepository.save(terrain);
    }
}
