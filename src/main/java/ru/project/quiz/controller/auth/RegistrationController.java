package ru.project.quiz.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.mapper.ituser.UserMapper;
import ru.project.quiz.service.ituser.ITUserService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Регистрация")
public class RegistrationController {
    private final ITUserService userService;
    private final UserMapper userMapper;
    public final String REGISTER = "/register";

    @Operation(summary = "Регистрация")
    @PostMapping(REGISTER)
    public ResponseEntity<String> registration(@Valid @RequestParam String username,
                                               @RequestParam String password, @RequestParam String email) {
        HttpHeaders responseHeaders = new HttpHeaders();
        ITUser user = userService.registerUser(username,password, email);
        responseHeaders.setLocation(URI.create("/api/users/" + user.getId()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

}
