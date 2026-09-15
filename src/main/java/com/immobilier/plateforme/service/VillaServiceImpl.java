package com.immobilier.plateforme.service;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.mapper.VillaMapper;
import com.immobilier.plateforme.model.dto.villa.CreateVillaRequestDTO;
import com.immobilier.plateforme.model.dto.villa.VillaResponseDTO;
import com.immobilier.plateforme.model.entity.Media;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.model.entity.Villa;
import com.immobilier.plateforme.repository.UserRepository;
import com.immobilier.plateforme.repository.VillaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VillaServiceImpl implements VillaService {

    private final VillaRepository villaRepository;
    private final FileStorageService fileStorageService;
    private final VillaMapper villaMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public VillaResponseDTO createVilla(CreateVillaRequestDTO dto, MultipartFile[] images) {

        User proprietaire = userRepository.findById(dto.getProprietaireId())
                .orElseThrow(() -> new RuntimeException("Propriétaire introuvable avec l'ID : " + dto.getProprietaireId()));

        Role role = proprietaire.getRole();
        boolean estAutorise = role == Role.PROPRIETAIRE_PART
                || role == Role.AGENCE_IMMOBILIERE
                || role == Role.ADMIN;

        if (!estAutorise) {
            throw new IllegalArgumentException("Seuls les propriétaires et agences immobilières peuvent publier un bien.");
        }

        if (proprietaire.getUserStatut() != UserStatut.ACTIF) {
            throw new IllegalStateException("Impossible de publier un bien : le compte de l'utilisateur n'est pas actif.");
        }

        Villa villa = new Villa();

        // Attributs communs hérités de Bien
        villa.setTitre(dto.getTitre());
        villa.setDescription(dto.getDescription());
        villa.setPrix(dto.getPrix());
        villa.setSurfaceHabitable(dto.getSurfaceHabitable());
        villa.setVille(dto.getVille());
        villa.setQuartier(dto.getQuartier());
        villa.setAdresse(dto.getAdresse());
        villa.setLatitude(dto.getLatitude());
        villa.setLongitude(dto.getLongitude());
        villa.setProprietaire(proprietaire);

        // Attributs spécifiques de Villa
        villa.setSurfaceTerrain(dto.getSurfaceTerrain());
        villa.setNombreChambres(dto.getNombreChambres());
        villa.setNombreFacades(dto.getNombreFacades() != null ? dto.getNombreFacades() : 4);
        villa.setJardin(dto.getJardin());
        villa.setSurfaceJardin(dto.getSurfaceJardin());
        villa.setPiscine(dto.getPiscine());
        villa.setGarage(dto.getGarage());

        // Traitement des fichiers multimédias
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
                        media.setBien(villa);

                        villa.getMedias().add(media);
                        isFirst = false;
                    }
                }
            }
        }

        Villa savedVilla = villaRepository.save(villa);
        return villaMapper.toResponseDTO(savedVilla);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VillaResponseDTO> getAllVillas(Pageable pageable) {
        return villaRepository.findAll(pageable)
                .map(villaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public VillaResponseDTO getVillaById(UUID id) {
        Villa villa = villaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Villa introuvable avec l'ID : " + id));
        return villaMapper.toResponseDTO(villa);
    }
}
