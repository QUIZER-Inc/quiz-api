package ru.project.quiz.service.Impl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.quiz.domain.entity.ITUser;
import ru.project.quiz.domain.entity.Role;
import ru.project.quiz.domain.enums.RoleType;
import ru.project.quiz.repository.UserRepository;
import ru.project.quiz.service.UserService;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            ITUser ITUser = optionalUser.get();
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
            return User.builder()
                    .username(ITUser.getUsername())
                    .password(ITUser.getPassword())
                    .authorities("USER")
                    .build();
        }
        throw new UsernameNotFoundException("User not found, sorry");
    }

    public boolean saveUser(ITUser user) {
        Optional<ITUser> optionalUser = userRepository.findUserByUsername(user.getUsername());
        if (!optionalUser.isPresent()) return false;
        Role role = new Role();
        role.setRole(RoleType.USER);
        role.setId(user.getId());
        Set<Role> set = new HashSet<>();
        set.add(role);
        user.setRoles(set);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
