package uz.market.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.market.backend.domain.Role;
import uz.market.backend.domain.User;
import uz.market.backend.repository.RoleRepository;
import uz.market.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }
    public User save(User user) {
        return userRepository.save(user);
    }
    public User registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Optional<Role> userRoleOpt = roleRepository.findByName("ROLE_USER");
        if (!userRoleOpt.isPresent()) {
            throw new RuntimeException("Role not found.");
        }

        Role userRole = userRoleOpt.get();
        user.getRoles().add(userRole);

        return userRepository.save(user);
    }
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
