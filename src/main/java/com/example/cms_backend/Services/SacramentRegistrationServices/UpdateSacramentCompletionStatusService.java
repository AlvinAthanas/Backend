package com.example.cms_backend.Services.SacramentRegistrationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.UpdateSacramentCompletionStatusCommand;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Repositories.SacramentRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateSacramentCompletionStatusService implements Command<UpdateSacramentCompletionStatusCommand, SacramentRegistration> {

    private final SacramentRegistrationRepository registrationRepository;

    public UpdateSacramentCompletionStatusService(SacramentRegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public ResponseEntity<SacramentRegistration> execute(UpdateSacramentCompletionStatusCommand command) {
        Optional<SacramentRegistration> optional = registrationRepository.findByCandidateIdAndSacramentType(
                command.getCandidateId(),
                command.getSacramentType()
        );

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SacramentRegistration registration = optional.get();
        registration.setCompleted(command.isCompleted());
        SacramentRegistration saved = registrationRepository.save(registration);
        return ResponseEntity.ok(saved);
    }
}
