package com.immobilier.plateforme.service;

import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.model.dto.UserAdminResponseDTO;
import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    public Page<User> getAllUsers(String search, int page, int size) {
        // Nettoyage de la chaîne de recherche
        String query = (search != null) ? search.trim() : "";
        Pageable pageable = PageRequest.of(page, size);

        return userRepository.findAllUsersForAdmin(query, pageable);
    }

    @Transactional
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        user.setUserStatut(user.getUserStatut() == UserStatut.ACTIF ? UserStatut.SUSPENDU : UserStatut.ACTIF);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}