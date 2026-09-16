package com.immobilier.plateforme.mapper;

import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import com.immobilier.plateforme.model.dto.terrain.TerrainResponseDTO;
import com.immobilier.plateforme.model.entity.Media;
import com.immobilier.plateforme.model.entity.Terrain;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TerrainMapper {

    public TerrainResponseDTO toResponseDTO(Terrain terrain) {
        if (terrain == null) {
            return null;
        }

        TerrainResponseDTO dto = new TerrainResponseDTO();

        // Champs hérités de Bien
        dto.setId(terrain.getId());
        dto.setTitre(terrain.getTitre());
        dto.setDescription(terrain.getDescription());
        dto.setPrix(terrain.getPrix());
        dto.setSurfaceHabitable(terrain.getSurfaceHabitable()); // Si présent dans Bien
        dto.setStatut(terrain.getStatut());
        dto.setVille(terrain.getVille());
        dto.setQuartier(terrain.getQuartier());
        dto.setAdresse(terrain.getAdresse());
        dto.setLatitude(terrain.getLatitude());
        dto.setLongitude(terrain.getLongitude());


        dto.setCreatedAt(terrain.getCreatedAt());

        // Champs spécifiques au Terrain
        dto.setTypeTerrain(terrain.getTypeTerrain());
        dto.setSuperficieTotale(terrain.getSuperficieTotale());
        dto.setNombreFacades(terrain.getNombreFacades());
        dto.setZonage(terrain.getZonage());
        dto.setViabilise(terrain.getViabilise());
        dto.setCloture(terrain.getCloture());
        dto.setTitreFoncier(terrain.getTitreFoncier());
        dto.setEau(terrain.getEau());
        dto.setElectricite(terrain.getElectricite());
        dto.setAccesGoudronne(terrain.getAccesGoudronne());
        dto.setAssainissement(terrain.getAssainissement());

        // Mapping de la liste des médias
        if (terrain.getMedias() != null) {
            List<MediaResponseDTO> mediaDTOs = new ArrayList<>();
            for (Media media : terrain.getMedias()) {
                MediaResponseDTO mediaDto = new MediaResponseDTO();
                mediaDto.setId(media.getId());
                mediaDto.setUrl(media.getUrl());
                mediaDto.setType(media.getType());
                mediaDto.setEstPrincipal(media.getEstPrincipal());
                mediaDTOs.add(mediaDto);
            }
            dto.setMedias(mediaDTOs);
        }

        return dto;
    }
}