package ru.project.quiz.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.project.quiz.domain.dto.ituser.RoleDTO;
import ru.project.quiz.domain.entity.ituser.Role;
import ru.project.quiz.mapper.ituser.RoleMapper;
import ru.project.quiz.service.ituser.RoleService;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/roles")
@Tag(name = "Контроллер ролей")
public class RoleController {
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public final String ROLE_BY_NAME = "/{role-name}";

    @Operation(summary = "Создать новую роль", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<String> addRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleMapper.roleFromRoleDTO(roleDTO);
        roleService.addNewRole(role);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(URI.create("/api/roles/" + role.getName()));
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Удалить роль", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping
    public ResponseEntity<String> deleteRole(@RequestParam String name) {
        roleService.deleteRole(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Получить роль по имени роли", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(ROLE_BY_NAME)
    public ResponseEntity<RoleDTO> findRoleByID(@PathVariable("role-name") String name) {
        Optional<Role> optRoleById = roleService.findRoleByName(name);
        return optRoleById
                .map(role -> new ResponseEntity<>(roleMapper.roleDTOFromRole(role), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
