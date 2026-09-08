package com.immobilier.plateforme.mapper;

import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import com.immobilier.plateforme.model.dto.MediaResponseDTO;
import com.immobilier.plateforme.model.dto.appartement.AppartementResponseDTO;
import com.immobilier.plateforme.model.entity.Appartement;
import com.immobilier.plateforme.model.entity.Media;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppartementMapper {

    public AppartementResponseDTO toResponseDTO(Appartement appartement) {
        if (appartement == null) {
            return null;
        }

        AppartementResponseDTO dto = new AppartementResponseDTO();
        dto.setId(appartement.getId());
        dto.setTitre(appartement.getTitre());
        dto.setDescription(appartement.getDescription());
        dto.setPrix(appartement.getPrix());
        dto.setSurfaceHabitable(appartement.getSurfaceHabitable());
        dto.setStatut(appartement.getStatut());
        dto.setVille(appartement.getVille());
        dto.setQuartier(appartement.getQuartier());
        dto.setAdresse(appartement.getAdresse());
        dto.setLatitude(appartement.getLatitude());
        dto.setLongitude(appartement.getLongitude());

        if (appartement.getProprietaire() != null) {
            dto.setProprietaireId(appartement.getProprietaire().getId());
        }
        dto.setCreatedAt(appartement.getCreatedAt());

        // Attributs spécifiques à Appartement
        dto.setNombreChambres(appartement.getNombreChambres());
        dto.setEstMeuble(appartement.getEstMeuble());
        dto.setEtage(appartement.getEtage());
        dto.setAscenseur(appartement.getAscenseur());
        dto.setPiscine(appartement.getPiscine());
        dto.setChargesMensuelles(appartement.getChargesMensuelles());
        dto.setSurfaceBalcon(appartement.getSurfaceBalcon());
        dto.setParkingInclus(appartement.getParkingInclus());

        // Mapping de la liste des médias
        if (appartement.getMedias() != null) {
            List<MediaResponseDTO> mediaDTOs = new ArrayList<>();
            for (Media media : appartement.getMedias()) {
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