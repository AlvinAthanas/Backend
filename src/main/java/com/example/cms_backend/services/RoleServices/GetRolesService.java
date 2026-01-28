package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetRolesService implements Query<Void, List<Role>> {
    private final RoleRepository roleRepository;

    public GetRolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<List<Role>> execute(Void input) {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
