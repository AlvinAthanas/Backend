package com.example.cms_backend.Services.SacramentRegistrationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.UpdateSacramentRegistrationCommand;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Repositories.SacramentRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateSacramentRegistrationService implements Command<UpdateSacramentRegistrationCommand, SacramentRegistration> {

    private final SacramentRegistrationRepository repository;

    public UpdateSacramentRegistrationService(SacramentRegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<SacramentRegistration> execute(UpdateSacramentRegistrationCommand command) {
        return repository.findById(command.getId())
                .map(existing -> {
                    existing.setSacramentType(command.getSacramentType());
                    existing.setCompleted(command.isCompleted());
                    existing.setCompletionDate(command.getCompletionDate());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

