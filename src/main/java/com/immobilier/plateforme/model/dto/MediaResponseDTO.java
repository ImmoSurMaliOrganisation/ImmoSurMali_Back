package com.immobilier.plateforme.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MediaResponseDTO {
    private UUID id;
    private String url;
    private String type;
    private Boolean estPrincipal;
}