package ru.project.quiz.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.project.quiz.service.ituser.ITUserService;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Регистрация")
public class RegistrationController {
    public final ITUserService userService;
    public final String REGISTER = "/register";

    @Operation(summary = "Регистрация")
    @PostMapping(REGISTER)
    public ResponseEntity<Response> registration(@RequestBody ITUserDTO ITUserDTO) {
        int userId=0; //костыль, надо переписать ITUserService
       // ITUser itUser= userService.saveUser(ITUserDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/users/"+userId));
        return new ResponseEntity<>(new Response("Register success"),responseHeaders, HttpStatus.CREATED);
    }

    public RegistrationController(ITUserService userService) {
        this.userService = userService;
    }
}
