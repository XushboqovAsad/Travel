package uz.market.backend.web.rest;

import uz.market.backend.DTO.AssignRoleRequest;
import uz.market.backend.domain.Role;
import uz.market.backend.domain.User;
import uz.market.backend.repository.RoleRepository;
import uz.market.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/assign-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> assignRole(@RequestBody AssignRoleRequest assignRoleRequest) {
        String username = assignRoleRequest.getUsername();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body("User not found: " + username);
        }

        User user = userOpt.get();

        for (AssignRoleRequest.RoleDto roleDto : assignRoleRequest.getRoles()) {
            Optional<Role> roleOpt = roleRepository.findByName(roleDto.getName());
            if (!roleOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Role not found: " + roleDto.getName());
            }

            Role role = roleOpt.get();
            user.getRoles().add(role);
        }

        userRepository.save(user);
        return ResponseEntity.ok("Roles assigned successfully to user: " + username);
    }
}
