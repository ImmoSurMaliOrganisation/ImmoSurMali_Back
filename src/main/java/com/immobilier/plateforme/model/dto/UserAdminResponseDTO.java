package com.immobilier.plateforme.model.dto;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminResponseDTO {
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private Role role;
    private UserStatut userStatut;
    private LocalDateTime dateInscription;
    private Long annoncesCount;
}