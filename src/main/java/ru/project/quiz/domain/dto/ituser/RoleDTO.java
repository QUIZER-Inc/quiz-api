package ru.project.quiz.domain.dto.ituser;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.enums.ituser.PermissionType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    private String name;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<PermissionType> permissions;

    public RoleDTO(String name, Set<PermissionType> permissions) {
        this.name = name;
        this.permissions = permissions;
    }
}
