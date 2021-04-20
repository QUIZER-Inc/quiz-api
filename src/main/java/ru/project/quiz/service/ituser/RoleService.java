package ru.project.quiz.service.ituser;

import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.domain.enums.ituser.PermissionType;

import java.util.List;

public interface RoleService {
    void addNewRole(String name, PermissionType permissionType);
    Role findRole(String name);
    void deleteRole(String name);

    List<ITUserDTO> findUsersByRole(String name);
}
