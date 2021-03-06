package ru.project.quiz.service.ituser;

import org.springframework.security.core.userdetails.UserDetails;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;

import java.util.List;

public interface ITUserService {
    ITUser saveUser(ITUserDTO ITUserDTO);

    UserDetails loadUserByUsername(String username);

    ITUser findUserByUsername(String name);

    ITUser editUser(ITUser user);

    List<ITUser> findUsersByRole(String name);
}
