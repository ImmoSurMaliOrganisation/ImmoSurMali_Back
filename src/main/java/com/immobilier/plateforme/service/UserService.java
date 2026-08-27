package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterProprietaireRequestDTO; // Ajout de l'import pour le propriétaire

public interface UserService {
    
    AuthResponseDTO registerClient(RegisterClientRequestDTO request);
    
    // Déclaration de la méthode pour la création de compte propriétaire
    AuthResponseDTO registerProprietaire(RegisterProprietaireRequestDTO request);
}
