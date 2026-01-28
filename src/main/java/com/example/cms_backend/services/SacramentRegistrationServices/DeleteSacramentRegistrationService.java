package com.example.cms_backend.services.SacramentRegistrationServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.repositories.SacramentRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteSacramentRegistrationService implements Command<Long, Void> {

    private final SacramentRegistrationRepository repository;

    public DeleteSacramentRegistrationService(SacramentRegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

