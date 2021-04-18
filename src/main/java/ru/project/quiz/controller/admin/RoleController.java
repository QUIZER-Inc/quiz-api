package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.service.ituser.RoleService;

import java.util.List;


@RestController
@RequestMapping("/api/roles")
@Tag(name = "Контроллер ролей")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

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

    @Operation(summary = "Лист пользователей с данной ролью", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{roleName}")
    public List<ITUserDTO> findUsersByRole(@PathVariable String roleName) {
        return roleService.findUsersByRole(roleName);
    }
}
