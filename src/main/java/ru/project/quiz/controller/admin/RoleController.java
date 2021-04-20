package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.dto.ituser.RoleDTO;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.mapper.ituser.RoleMapper;
import ru.project.quiz.service.ituser.RoleService;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping("/api/roles")
@Tag(name = "Контроллер ролей")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Operation(summary = "Создать новую роль", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public void addNewRole(@RequestParam String name, @RequestParam PermissionType permissionType) {
        roleService.addNewRole(name, permissionType);
    }

    @Operation(summary = "Удалить роль", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public void deleteRole(@RequestParam String name) {
        roleService.deleteRole(name);
    }

    //TODO ISSUE#36
    @Operation(summary = "Лист пользователей с данной ролью", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/list/{roleName}")
    public List<ITUserDTO> findUsersByRole(@PathVariable String roleName) {
        return roleService.findUsersByRole(roleName);
    }

    @Operation(summary = "Получить роль по ID", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{role-name}")
    public ResponseEntity<RoleDTO> findRoleByID(@PathVariable("role-name") String name) {
        Optional<Role> optRoleById = roleService.findRoleByName(name);
        return optRoleById
                .map(role -> new ResponseEntity<>(roleMapper.roleDTOFromRole(role), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
