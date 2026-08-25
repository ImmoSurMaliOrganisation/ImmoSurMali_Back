package com.immobilier.plateforme.model.dto.auth; 

import lombok.Data;

@Data
public class RegisterClientRequestDTO {
    private String nom;
    private String email;
    private String motDePasse;
    private String telephone;
}
