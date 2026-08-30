package com.immobilier.plateforme.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAgenceRequestDTO {

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    private String nomAgence;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format de l'e-mail invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @NotBlank(message = "Le numéro RCCM est obligatoire")
    @Pattern(
        regexp = "^M[L|A]-[A-Z]{3}-\\d{4}-[A|B]-\\d{1,6}$", 
        message = "Format de RCCM invalide"
    )
    private String rccm;

    @Pattern(
        regexp = "^\\d{9}[A-Z]$", 
        message = "Format du NIF invalide"
    )
    private String nif; // Optionnel : s'il est fourni, la Regex s'applique, sinon il peut être absent

    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;
}
