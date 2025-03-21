package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Repositories.RoleRepository;
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
