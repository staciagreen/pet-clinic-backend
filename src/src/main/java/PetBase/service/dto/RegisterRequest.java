// PetBase.service.dto.RegisterRequest.java
package PetBase.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на регистрацию нового владельца")
public record RegisterRequest(
        @Schema(description = "Уникальное имя пользователя (логин)", example = "user1")
        String username,
        @Schema(description = "Пароль", example = "StrongP@ssw0rd")
        String password,
        @Schema(description = "Имя владельца", example = "Наташа")
        String name,
        @Schema(description = "Дата рождения", example = "1990-06-15")
        String birthDate
) {}
