package ru.project.quiz.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.handler.response.Response;
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
    public ResponseEntity<String> registration(@Valid @RequestBody ITUserDTO itUserDTO) {
        ITUser user = userMapper.userFromUserDTO(itUserDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/users/" + user.getId() + "/"));
        userService.saveUser(itUserDTO);
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

}
