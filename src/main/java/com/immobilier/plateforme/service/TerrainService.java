package com.immobilier.plateforme.service;

import com.immobilier.plateforme.mapper.TerrainMapper;
import com.immobilier.plateforme.model.dto.terrain.CreateTerrainRequestDTO;
import com.immobilier.plateforme.model.dto.terrain.TerrainResponseDTO;
import com.immobilier.plateforme.model.entity.Media;
import com.immobilier.plateforme.model.entity.Terrain;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.TerrainRepository;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TerrainService {

    private final TerrainRepository terrainRepository;
    private final UserRepository userRepository;
    private final TerrainMapper terrainMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public TerrainResponseDTO createTerrain(CreateTerrainRequestDTO dto, MultipartFile[] images) {
        Terrain terrain = new Terrain();

        // 1. Mappage des champs communs (hérités de Bien)
        terrain.setTitre(dto.getTitre());
        terrain.setDescription(dto.getDescription());
        terrain.setPrix(dto.getPrix());
        terrain.setSurfaceHabitable(dto.getSurfaceHabitable());
        terrain.setVille(dto.getVille());
        terrain.setQuartier(dto.getQuartier());
        terrain.setAdresse(dto.getAdresse());
        terrain.setLatitude(dto.getLatitude());
        terrain.setLongitude(dto.getLongitude());

        // 2. Récupération sécurisée du propriétaire via le token de l'utilisateur connecté
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User proprietaire = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        terrain.setProprietaire(proprietaire);

        // 3. Mappage des champs spécifiques au Terrain
        terrain.setTypeTerrain(dto.getTypeTerrain());
        terrain.setSuperficieTotale(dto.getSuperficieTotale());
        terrain.setNombreFacades(dto.getNombreFacades());
        terrain.setZonage(dto.getZonage());
        terrain.setViabilise(dto.getViabilise());
        terrain.setCloture(dto.getCloture());
        terrain.setTitreFoncier(dto.getTitreFoncier());
        terrain.setEau(dto.getEau());
        terrain.setElectricite(dto.getElectricite());
        terrain.setAccesGoudronne(dto.getAccesGoudronne());
        terrain.setAssainissement(dto.getAssainissement());

        // 4. Enregistrement des fichiers via votre FileStorageService et association aux médias
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                MultipartFile file = images[i];
                if (file != null && !file.isEmpty()) {
                    String fileUrl = fileStorageService.storeFile(file);
                    if (fileUrl != null) {
                        Media media = new Media();
                        media.setUrl(fileUrl);
                        media.setType("IMAGE");
                        media.setEstPrincipal(i == 0); // Le premier média devient l'image principale
                        media.setBien(terrain);
                        terrain.getMedias().add(media);
                    }
                }
            }
        }

        // 5. Sauvegarde et conversion via votre TerrainMapper
        Terrain savedTerrain = terrainRepository.save(terrain);
        return terrainMapper.toResponseDTO(savedTerrain);
    }

    @Transactional(readOnly = true)
    public Page<TerrainResponseDTO> getAllTerrains(Pageable pageable) {
        return terrainRepository.findAll(pageable).map(terrainMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public TerrainResponseDTO getTerrainById(UUID id) {
        Terrain terrain = terrainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Terrain introuvable avec l'ID : " + id));
        return terrainMapper.toResponseDTO(terrain);
    }
}