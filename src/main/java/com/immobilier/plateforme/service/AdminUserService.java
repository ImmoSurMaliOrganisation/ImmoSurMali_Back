package com.immobilier.plateforme.service;
import com.immobilier.plateforme.enums.Role;
import com.immobilier.plateforme.enums.UserStatut;
import com.immobilier.plateforme.exception.ResourceNotFoundException;
import com.immobilier.plateforme.model.dto.RejetAgenceRequestDTO;

import com.immobilier.plateforme.model.entity.User;
import com.immobilier.plateforme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private static final Logger log = LoggerFactory.getLogger(AdminUserService.class);
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
    public User updateUserStatus(Long id, UserStatut newStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'id : " + id));

        if (user.getRole() == Role.ADMIN) {
            throw new IllegalStateException("Impossible de modifier le statut d'un administrateur.");
        }

        user.setUserStatut(newStatus);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Rejette la demande d'inscription d'une agence avec un motif obligatoire (Tâche API Rejet)
     */
    @Transactional
    public User rejeterAgence(Long id, RejetAgenceRequestDTO dto) {
        // 1. Rechercher l'utilisateur/agence dans la base
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence immobilière introuvable avec l'id : " + id));

        // 2. Vérifier que c'est bien une agence et qu'elle est en attente
        if (user.getRole() != Role.AGENCE_IMMOBILIERE) {
            throw new IllegalArgumentException("Cet utilisateur n'est pas une agence immobilière.");
        }
        if (user.getUserStatut() != UserStatut.EN_ATTENTE) {
            throw new IllegalArgumentException("Impossible de rejeter cette demande. Statut actuel : " + user.getUserStatut());
        }

        // 3. Mettre à jour les statuts requis par les critères d'acceptation
        user.setUserStatut(UserStatut.REFUSE);
        user.setIsVerifier(false);

        // 4. Déclencher la simulation obligatoire d'envoi d'e-mail avec le motif exact
        log.info("==========================================================================");
        log.info("--> ✉️ ENVOI DE L'EMAIL DE REFUS (SIMULATION DE NOTIFICATION)");
        log.info("--> Destinataire : {}", user.getEmail());
        log.info("--> Objet : Refus de votre demande d'inscription - ImmoSurMali");
        log.info("--> Motif exact du rejet : {}", dto.getMotifRejet());
        log.info("==========================================================================");

        // 5. Sauvegarder les modifications dans PostgreSQL
        return userRepository.save(user);
    }
}
