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

    @Transactional(readOnly = true)
    public Page<User> searchUsers(Role role, String search, Pageable pageable) {
        return userRepository.findUsersWithFilters(role, search, pageable);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id : " + id));
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