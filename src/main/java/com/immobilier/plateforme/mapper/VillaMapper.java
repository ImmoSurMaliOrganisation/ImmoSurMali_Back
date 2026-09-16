package com.immobilier.plateforme.mapper;

import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import com.immobilier.plateforme.model.dto.villa.VillaResponseDTO;
import com.immobilier.plateforme.model.entity.Media;
import com.immobilier.plateforme.model.entity.Villa;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VillaMapper {

    public VillaResponseDTO toResponseDTO(Villa villa) {
        if (villa == null) {
            return null;
        }

        VillaResponseDTO dto = new VillaResponseDTO();

        // Champs hérités de Bien
        dto.setId(villa.getId());
        dto.setTitre(villa.getTitre());
        dto.setDescription(villa.getDescription());
        dto.setPrix(villa.getPrix());
        dto.setSurfaceHabitable(villa.getSurfaceHabitable());
        dto.setStatut(villa.getStatut());
        dto.setVille(villa.getVille());
        dto.setQuartier(villa.getQuartier());
        dto.setAdresse(villa.getAdresse());
        dto.setLatitude(villa.getLatitude());
        dto.setLongitude(villa.getLongitude());

        dto.setCreatedAt(villa.getCreatedAt());

        // Champs spécifiques à Villa
        dto.setSurfaceTerrain(villa.getSurfaceTerrain());
        dto.setNombreChambres(villa.getNombreChambres());
        dto.setNombreFacades(villa.getNombreFacades());
        dto.setJardin(villa.getJardin());
        dto.setSurfaceJardin(villa.getSurfaceJardin());
        dto.setPiscine(villa.getPiscine());
        dto.setGarage(villa.getGarage());

        // Mapping de la liste des médias
        if (villa.getMedias() != null) {
            List<MediaResponseDTO> mediaDTOs = new ArrayList<>();
            for (Media media : villa.getMedias()) {
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