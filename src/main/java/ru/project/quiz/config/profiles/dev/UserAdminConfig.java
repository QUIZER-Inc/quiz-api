package ru.project.quiz.config.profiles.dev;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.jwt.util.JwtTokenUtil;
import ru.project.quiz.repository.ituser.RoleRepository;
import ru.project.quiz.repository.ituser.UserRepository;
import ru.project.quiz.service.ituser.ITUserService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
@Profile("dev")
public class UserAdminConfig {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ITUserService itUserService;
    private final JwtTokenUtil jwtTokenUtil;

    @Bean
    private void setUpAdmin() {
        ITUser user = new ITUser();
        user.setUsername("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));
        Role role = new Role("ADMIN",
                Arrays.stream(PermissionType.values()).collect(Collectors.toSet())
        );
        roleRepository.save(role);
        user.setEmail("test@mail.ru");
        user.setRoles(Set.of(role));
        userRepository.save(user);

        final UserDetails userDetails = itUserService.loadUserByUsername("admin");
        final String token = jwtTokenUtil.generateToken(userDetails);

        log.info("\n\n\nТОКЕН:\n{}\n\n\n", token);
    }
}
