package PetBase.security;

import PetBase.dao.Repository.PetRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Служба для проверки прав владения.
 */
@Component("securityService")
public class SecurityService {

    private final PetRepository petRepo;

    public SecurityService(PetRepository petRepo) {
        this.petRepo = petRepo;
    }

    /**
     * Проверяет, что текущий аутентифицированный пользователь — владелец питомца с данным id.
     */
    public boolean isPetOwner(Long petId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof OwnerDetails))
            return false;
        Long currentOwnerId = ((OwnerDetails) auth.getPrincipal()).getId();
        return petRepo.findById(petId)
                .map(p -> p.getOwner().getId().equals(currentOwnerId))
                .orElse(false);
    }

    /**
     * Проверяет, что текущий пользователь имеет именно этот ownerId.
     * Удобно для OwnerRestController.
     */
    public boolean isOwner(Long ownerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof OwnerDetails))
            return false;
        return ((OwnerDetails) auth.getPrincipal()).getId().equals(ownerId);
    }
}
