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

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Контроллер пользователей")
public class UserController {

    private final ITUserService userService;

    public UserController(ITUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Поиск пользователя с данным username", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{name}")
    public ITUserDTO findUserByName(@PathVariable String name) {
        return userService.findUserByUsername(name);
    }

    @Operation(summary = "Редактирование пользователя по username", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/")
    public ResponseEntity<Response> editUserByName(@RequestBody ITUser user) {
        userService.editUser(user);
        return new ResponseEntity<>(new Response("User has been edited"), HttpStatus.OK);
    }
}
