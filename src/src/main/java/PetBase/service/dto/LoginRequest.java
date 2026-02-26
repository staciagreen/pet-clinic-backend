// PetBase.service.dto.LoginRequest.java
package PetBase.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на вход в систему")
public record LoginRequest(
        @Schema(description = "Имя пользователя (логин)", example = "user1")
        String username,
        @Schema(description = "Пароль", example = "StrongP@ssw0rd")
        String password
) {}
