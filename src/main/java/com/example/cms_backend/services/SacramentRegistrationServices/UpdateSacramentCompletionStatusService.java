package com.example.cms_backend.services.SacramentRegistrationServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Commands.UpdateSacramentCompletionStatusCommand;
import com.example.cms_backend.model.Entities.SacramentRegistration;
import com.example.cms_backend.repositories.SacramentRegistrationRepository;
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
