package com.immobilier.plateforme.service;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;

public interface UserService {
    AuthResponseDTO registerClient(RegisterClientRequestDTO request);
}
