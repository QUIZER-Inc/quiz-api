package ru.project.quiz.service.ituser.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.repository.ituser.RoleRepository;
import ru.project.quiz.service.ituser.RoleService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(String name) {
        Optional<Role> optRole = roleRepository.findByName(name);
        if (optRole.isEmpty()) {
            throw new QuizAPPException("Роль не существует");
        }
        Role role = optRole.get();
        roleRepository.delete(role);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
