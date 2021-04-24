package ru.project.quiz.domain.dto.ituser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Schema(description = "Сущность пользователя")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ITUserDTO {
    @Schema(description = "ID")
    private long id;

    @NotBlank(message = "Имя пользователя не должно быть пустым")
    @Schema(description = "Имя пользователя")
    private String username;

    @Schema(description = "Пароль")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;

    @Email
    @NotBlank
    private String email;

    @Schema(description = "roles")
    private Set<RoleDTO> roles;

    public ITUserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
