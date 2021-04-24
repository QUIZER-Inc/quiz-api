package ru.project.quiz.domain.dto.ituser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.enums.ituser.PermissionType;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private String name;

    private Set<PermissionType> permissions;

}
