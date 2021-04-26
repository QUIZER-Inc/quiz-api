package ru.project.quiz.service.ituser.Impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.handler.exception.QuizAPPException;
import ru.project.quiz.mapper.ituser.UserMapper;
import ru.project.quiz.repository.ituser.RoleRepository;
import ru.project.quiz.repository.ituser.UserRepository;
import ru.project.quiz.service.ituser.ITUserService;
import ru.project.quiz.service.mail.MailService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ITUserServiceImpl implements UserDetailsService, ITUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final Validator validator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("User not found, sorry");
    }

    @Override
    public ITUser findUserByUsername(String name) {
        Optional<ITUser> optUser = userRepository.findUserByUsername(name);
        if (optUser.isEmpty()) {
            throw new QuizAPPException("Данный пользователь не найден!");
        }
        return optUser.get();
    }

    @Override
    public ITUser editUser(ITUser user) {
        Optional<ITUser> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()) {
            if (bCryptPasswordEncoder.matches(user.getPassword(), optUser.get().getPassword())) {
                user.setPassword(optUser.get().getPassword());
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            return userRepository.save(user);
        }
        return user;
    }

    @Override
    public ITUser saveUser(ITUserDTO itUserDTO) {
        Set<ConstraintViolation<ITUserDTO>> violations = validator.validate(itUserDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(itUserDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new QuizAPPException("Данный пользователь существует");
        } else {
            String email = itUserDTO.getEmail();
            if (userRepository.existsByEmail(email)) {
                throw new QuizAPPException("Пользователь с данной почтой уже существует");
            }
            ITUser user = new ITUser();
            user.setUsername(itUserDTO.getUsername());
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(itUserDTO.getPassword()));
            Role role = new Role("USER", Set.of(PermissionType.GENERATE_TESTS));
            roleRepository.save(role);
            user.setRoles(Set.of(role));
            userRepository.save(user);
            mailService.registrationSuccessfulMessage(email);
            return user;
        }
    }

    public List<ITUser> findUsersByRole(String name) {
        List<ITUser> list = userRepository.findITUsersByRoleName(name);
        if (list.isEmpty()) {
            throw new RuntimeException("Юзеры с данной ролью не найдены");
        }
        return list;
    }

    @Override
    public ITUser findUserById(long id) {
        Optional<ITUser> optionalITUser = userRepository.findById(id);
        if(optionalITUser.isEmpty()){
            throw new QuizAPPException("Юзер с данным ID не найден");
        }
        return optionalITUser.get();
    }
}