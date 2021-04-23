package ru.project.quiz.service.ituser;

import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.dto.ituser.RoleDTO;
import ru.project.quiz.domain.entity.ituser.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    void addNewRole(Role role);

    void deleteRole(String name);

    List<ITUserDTO> findUsersByRole(String name);

    Optional<Role> findRoleByName(String name);
}
