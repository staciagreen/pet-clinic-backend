package PetBase.rest;

import PetBase.service.dto.OwnerDTO;
import PetBase.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final OwnerService ownerService;

    public AuthRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Получить данные текущего пользователя
    @GetMapping("/me")
    public ResponseEntity<OwnerDTO> getCurrent() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(ownerService.findByUsername(username));
    }

    // Завершить сессию (для formLogin/logout)
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Spring Security настроит /logout автоматически,
        // но если нужно – сюда можно кинуть логику.
        return ResponseEntity.noContent().build();
    }
}
