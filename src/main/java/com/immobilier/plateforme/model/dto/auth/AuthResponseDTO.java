package com.immobilier.plateforme.model.dto.auth;

import com.immobilier.plateforme.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private String email;

    private Role role;
}