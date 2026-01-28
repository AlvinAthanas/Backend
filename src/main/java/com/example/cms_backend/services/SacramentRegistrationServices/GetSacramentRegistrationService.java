package com.example.cms_backend.services.SacramentRegistrationServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.SacramentRegistration;
import com.example.cms_backend.repositories.SacramentRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetSacramentRegistrationService implements Query<Long, SacramentRegistration> {

    private final SacramentRegistrationRepository repository;

    public GetSacramentRegistrationService(SacramentRegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<SacramentRegistration> execute(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
