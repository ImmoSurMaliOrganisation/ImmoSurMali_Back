package com.immobilier.plateforme.controller;

import com.immobilier.plateforme.model.dto.auth.AuthResponseDTO;
import com.immobilier.plateforme.model.dto.auth.RegisterClientRequestDTO;
import com.immobilier.plateforme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users") 
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/register/client") 
    public ResponseEntity<AuthResponseDTO> registerClient(@RequestBody RegisterClientRequestDTO request) {
        AuthResponseDTO response = userService.registerClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
