package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ParishNotFoundException;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.UpdateCommands.UpdateParishCommand;
import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateParishService implements Command<UpdateParishCommand, Parish> {
    private final ParishRepository parishRepository;

    public UpdateParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<Parish> execute(UpdateParishCommand command) {
        Optional<Parish> parishOptional = parishRepository.findById(command.getId());
        if (parishOptional.isPresent()) {
            Parish parish = command.getParish();
            parish.setId(command.getId());
            parishRepository.save(parish);
            return ResponseEntity.ok(parish);
        }
        throw new ParishNotFoundException();
    }
}
