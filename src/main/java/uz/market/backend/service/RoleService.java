package uz.market.backend.service;

import org.springframework.stereotype.Service;
import uz.market.backend.domain.Role;
import uz.market.backend.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
