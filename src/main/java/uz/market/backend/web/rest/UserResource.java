package uz.market.backend.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.market.backend.domain.User;
import uz.market.backend.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // To'ldirilmagan `firstName` va `lastName` uchun xato qaytariladi
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("First name is required");
        }

        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Last name is required");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PutMapping("/profile/edit")
    public ResponseEntity editProfile(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.ok("Error");
        }
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/account/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @GetMapping("/users")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
