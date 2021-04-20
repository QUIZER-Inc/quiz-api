package ru.project.quiz.service.ituser.Impl;

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
import ru.project.quiz.handler.exception.IncorrectInputUserException;
import ru.project.quiz.mapper.ituser.UserMapper;
import ru.project.quiz.repository.ituser.RoleRepository;
import ru.project.quiz.repository.ituser.UserRepository;
import ru.project.quiz.service.ituser.ITUserService;
import ru.project.quiz.service.mail.MailService;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class ITUserServiceImpl implements UserDetailsService, ITUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final Validator validator;

    Logger log = LoggerFactory.getLogger(ITUserServiceImpl.class);

    public ITUserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, UserMapper userMapper, Validator validator) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.validator = validator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            return userMapper.userDTOFromUser(optionalUser.get());
        }
        throw new UsernameNotFoundException("User not found, sorry");
    }

    @Override
    public ITUserDTO findUserByUsername(String name) {
        Optional<ITUser> optUser = userRepository.findUserByUsername(name);
        if(optUser.isEmpty()){
            throw new RuntimeException("Данный пользователь не найден!");
        }
        return userMapper.userDTOFromUser(optUser.get());
    }

    @Override
    public ITUser editUser(ITUser user) throws UserPrincipalNotFoundException {
        Optional<ITUser> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()){
            if (bCryptPasswordEncoder.matches(user.getPassword(), optUser.get().getPassword())){
                user.setPassword(optUser.get().getPassword());
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
          return userRepository.save(user);
        }
        else throw new UserPrincipalNotFoundException("User "+user.getUsername()+" not found");
    }

    @Override
    public void saveUser(ITUserDTO itUserDTO) {
        Set<ConstraintViolation<ITUserDTO>> violations = validator.validate(itUserDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(itUserDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new IncorrectInputUserException("Данный пользователь существует");
        } else {
            String email = itUserDTO.getEmail();
            if (userRepository.existsByEmail(email)) {
                throw new IncorrectInputUserException("Пользователь с данной почтой уже существует");
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
        }
    }


    @Override
    public void setNewRole(String username, String roleName) {
        Optional<ITUser> optionalITUser = userRepository.findUserByUsername(username);
        if (optionalITUser.isEmpty()) {
            log.error("Пользователя с username: {} не существует", username);
            throw new IncorrectInputUserException("Данный пользователь не существует");
        } else {
            ITUser user = optionalITUser.get();
            //TODO поиск роли по имени
            Optional<Role> roleOptional = roleRepository.findByName(roleName);
            if (roleOptional.isEmpty()) {
                log.error("Роли {} не существует", roleName);
                throw new RuntimeException("Такой роли не существует");
            }
            user.getRoles().add(roleOptional.get());
            userRepository.save(user);
        }
    }
}