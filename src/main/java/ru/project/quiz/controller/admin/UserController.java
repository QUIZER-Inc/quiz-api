package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.entity.ituser.ITUser;
import ru.project.quiz.mapper.ituser.UserMapper;
import ru.project.quiz.service.ituser.ITUserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Контроллер пользователей")
public class UserController {

    private final ITUserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Поиск пользователя с данным username", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{name}")
    public ResponseEntity<ITUserDTO> findUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userService.findUserByUsername(name), HttpStatus.OK);
    }

    @Operation(summary = "Редактирование пользователя по username", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping
    public ResponseEntity<ITUserDTO> editUserByName(@RequestBody ITUser user) {
        return new ResponseEntity<>(userMapper.userDTOFromUser(userService.editUser(user)), HttpStatus.OK);
    }

    //TODO ISSUE#36
    @Operation(summary = "Дать роль пользователю", security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{username}")
    public ResponseEntity<ITUserDTO> setRoleToUser(@PathVariable String username, @RequestParam String roleName) {
        return new ResponseEntity<>(userMapper.userDTOFromUser(userService.setNewRole(username, roleName)), HttpStatus.OK);
    }

    @Operation(summary = "Лист пользователей с данной ролью", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<List<ITUserDTO>> findUsersByRole(@RequestParam String role) {
        return new ResponseEntity<>(userMapper.listITUsersDTOFromListITUsers(userService.findUsersByRole(role)), HttpStatus.OK);
    }
}
