package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateParishService implements Command<Parish, Parish> {
    private final RoleRepository roleRepository;
    private final ParishRepository parishRepository;

    public CreateParishService(RoleRepository roleRepository, ParishRepository parishRepository) {
        this.roleRepository = roleRepository;
        this.parishRepository = parishRepository;
    }


    @Override
    public ResponseEntity<Parish> execute(Parish parish) {
        parishRepository.save(parish);
        return ResponseEntity.status(HttpStatus.CREATED).body(parish);
    }
}
