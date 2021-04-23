package ru.project.quiz.service.ituser.Impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.mapper.ituser.UserMapper;
import ru.project.quiz.repository.ituser.RoleRepository;
import ru.project.quiz.repository.ituser.UserRepository;
import ru.project.quiz.service.ituser.RoleService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void addNewRole(Role role) {
        roleRepository.save(role);
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
    public List<ITUserDTO> findUsersByRole(String name) {
        List<ITUser> list = userRepository.findITUsersByRoleName(name);
        List<ITUserDTO> listDTO = userMapper.listITUsersDTOFromListITUsers(list);
        if (list.isEmpty()) {
            throw new QuizAPPException("Юзеры с данной ролью не найдены");
        }
        return listDTO;
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return roleRepository.findByName(name);
    }


}
