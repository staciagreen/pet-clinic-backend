package PetBase.rest;

import PetBase.controller.dto.OwnerDTO;
import PetBase.controller.mapper.OwnerDtoMapper;
import PetBase.service.OwnerService;
import PetBase.service.dto.RegisterRequest;
import PetBase.service.dto.LoginRequest;
import PetBase.service.dto.OwnerEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final OwnerService ownerService;
    private final AuthenticationManager authManager;

    public AuthRestController(OwnerService ownerService,
                              AuthenticationManager authManager) {
        this.ownerService = ownerService;
        this.authManager = authManager;
    }

    @PostMapping("/register")
    public ResponseEntity<OwnerDTO> register(@RequestBody RegisterRequest req) {
        var svcDto = ownerService.registerNewOwner(
                req.username(), req.password(), req.name(), req.birthDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(OwnerDtoMapper.toDto(svcDto));
    }

    /** Вход: проверяем creds и создаём Authentication в контексте */
    @PostMapping("/login")
    public ResponseEntity<OwnerEntityDto> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        OwnerEntityDto me = ownerService.findByUsername(req.username());
        return ResponseEntity.ok(me);
    }

    /** Получить данные текущего пользователя */
    @GetMapping("/me")
    public ResponseEntity<OwnerEntityDto> getCurrent() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return ResponseEntity.ok(ownerService.findByUsername(username));
    }

    /** Завершение сессии */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
