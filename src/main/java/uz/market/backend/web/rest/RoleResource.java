package uz.market.backend.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.market.backend.domain.Role;
import uz.market.backend.service.RoleService;

@RestController
@RequestMapping("/api")
public class RoleResource {
    private final RoleService roleService;

    public RoleResource(RoleService roleService) {
        this.roleService = roleService;
    }
    @PostMapping("/role")
    public ResponseEntity create(@RequestBody Role role) {
        Role result = roleService.save(role);
        return ResponseEntity.ok(result);
    }
}
