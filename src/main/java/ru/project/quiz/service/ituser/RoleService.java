package ru.project.quiz.service.ituser;

import ru.project.quiz.domain.entity.ituser.Role;

import java.util.Optional;

public interface RoleService {
    Role addNewRole(Role role);

    void deleteRole(String name);

    Optional<Role> findRoleByName(String name);
}
