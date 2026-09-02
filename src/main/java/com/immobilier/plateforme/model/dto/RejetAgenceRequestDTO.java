package com.immobilier.plateforme.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejetAgenceRequestDTO {

    @NotBlank(message = "Le motif du rejet est obligatoire")
    private String motifRejet;
}
