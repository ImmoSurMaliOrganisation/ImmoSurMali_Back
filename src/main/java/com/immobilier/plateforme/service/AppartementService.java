package com.immobilier.plateforme.service;


import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.mapper.AppartementMapper;
import com.immobilier.plateforme.model.dto.appartement.AppartementResponseDTO;
import com.immobilier.plateforme.model.dto.appartement.CreateAppartementRequestDTO;
import com.immobilier.plateforme.model.entity.Appartement;
import com.immobilier.plateforme.model.entity.Media;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.AppartementRepository;
import com.immobilier.plateforme.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AppartementService {

    private final AppartementRepository appartementRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final AppartementMapper appartementMapper;

    @Transactional
    public AppartementResponseDTO createAppartement(CreateAppartementRequestDTO dto, MultipartFile[] images) {
        User proprietaire = userRepository.findById(dto.getProprietaireId())
                .orElseThrow(() -> new RuntimeException("Propriétaire introuvable avec l'ID : " + dto.getProprietaireId()));

        Role role = proprietaire.getRole();
        boolean estAutorise = role == Role.PROPRIETAIRE_PART
                || role == Role.AGENCE_IMMOBILIERE
                || role == Role.ADMIN;

        if (!estAutorise) {
            throw new IllegalArgumentException("Seuls les propriétaires et les agences immobilières peuvent publier un bien.");
        }

        if (proprietaire.getUserStatut() != UserStatut.ACTIF) {
            throw new IllegalStateException("Impossible de publier un bien : le compte de l'utilisateur n'est pas actif.");
        }

        Appartement appartement = new Appartement();
        appartement.setTitre(dto.getTitre());
        appartement.setDescription(dto.getDescription());
        appartement.setPrix(dto.getPrix());
        appartement.setSurfaceHabitable(dto.getSurfaceHabitable());
        appartement.setVille(dto.getVille());
        appartement.setQuartier(dto.getQuartier());
        appartement.setAdresse(dto.getAdresse());
        appartement.setLatitude(dto.getLatitude());
        appartement.setLongitude(dto.getLongitude());
        appartement.setProprietaire(proprietaire);

        appartement.setNombreChambres(dto.getNombreChambres());
        appartement.setEstMeuble(dto.getEstMeuble());
        appartement.setEtage(dto.getEtage());
        appartement.setAscenseur(dto.getAscenseur());
        appartement.setPiscine(dto.getPiscine());
        appartement.setChargesMensuelles(dto.getChargesMensuelles());
        appartement.setSurfaceBalcon(dto.getSurfaceBalcon());
        appartement.setParkingInclus(dto.getParkingInclus());

        if (images != null && images.length > 0) {
            boolean isFirst = true;
            for (MultipartFile file : images) {
                if (file != null && !file.isEmpty()) {
                    String filePath = fileStorageService.storeFile(file);

                    if (filePath != null) {
                        Media media = new Media();
                        media.setUrl(filePath);
                        media.setType("IMAGE");
                        media.setEstPrincipal(isFirst);
                        media.setBien(appartement);

                        appartement.getMedias().add(media);
                        isFirst = false;
                    }
                }
            }
        }

        Appartement savedAppartement = appartementRepository.save(appartement);
        return appartementMapper.toResponseDTO(savedAppartement);
    }

    @Transactional(readOnly = true)
    public Page<AppartementResponseDTO> getAllAppartements(Pageable pageable) {
        return appartementRepository.findAll(pageable)
                .map(appartementMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public AppartementResponseDTO getAppartementById(UUID id) {
        Appartement appartement = appartementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appartement introuvable avec l'ID : " + id));
        return appartementMapper.toResponseDTO(appartement);
    }
}
