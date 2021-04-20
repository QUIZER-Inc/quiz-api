package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.ITUserDTO;
import ru.project.quiz.domain.dto.ituser.RoleDTO;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.mapper.ituser.RoleMapper;
import ru.project.quiz.service.ituser.RoleService;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/roles")
@Tag(name = "Контроллер ролей")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @Operation(summary = "Создать новую роль", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<String> addRole(@RequestParam String name, @RequestParam PermissionType permissionType) {
        roleService.addNewRole(name, permissionType);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/roles"));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }
    @Operation(summary = "Получить роль", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping
    public ResponseEntity<RoleDTO> getRole(@RequestParam String name) {
        return new ResponseEntity<>(roleMapper.roleDTOFromRole(roleService.findRole(name)), HttpStatus.OK);
    }

    @Operation(summary = "Удалить роль", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<String> deleteRole(@RequestParam String name) {
        roleService.deleteRole(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Лист пользователей с данной ролью", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{roleName}")
    public ResponseEntity<List<ITUserDTO>> findUsersByRole(@PathVariable String roleName) {
        return new ResponseEntity<>(roleService.findUsersByRole(roleName), HttpStatus.OK);
    }
}
