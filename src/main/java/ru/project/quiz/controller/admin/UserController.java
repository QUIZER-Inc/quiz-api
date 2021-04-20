package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.handler.response.Response;
import ru.project.quiz.service.ituser.ITUserService;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Контроллер пользователей")
public class UserController {

    private final ITUserService userService;

    public UserController(ITUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователя с данным username", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{name}")
    public ResponseEntity<ITUserDTO> findUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userService.findUserByUsername(name),HttpStatus.OK);
    }

    @Operation(summary = "Редактирование пользователя по username", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping
    public ResponseEntity<ITUser> editUserByName(@RequestBody ITUser user) throws UserPrincipalNotFoundException {
        return new ResponseEntity<>(userService.editUser(user), HttpStatus.OK);
    }
}
