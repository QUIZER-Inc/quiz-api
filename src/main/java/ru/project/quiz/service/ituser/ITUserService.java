package ru.project.quiz.service.ituser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;

import java.util.List;

public interface ITUserService {
    ITUser registerUser(String username, String password, String email);

    UserDetails loadUserByUsername(String username);

    ITUser findUserByUsername(String name);

    ITUser editUser(ITUser user);

    List<ITUser> findUsersByRole(String name);

    ITUser findUserById(long id);
}
