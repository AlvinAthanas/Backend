package com.example.cms_backend.Services.SacramentRegistrationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Repositories.SacramentRegistrationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSacramentRegistrationsService implements Query<Void, List<SacramentRegistration>> {

    private final SacramentRegistrationRepository repository;

    public GetSacramentRegistrationsService(SacramentRegistrationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<List<SacramentRegistration>> execute(Void unused) {
        return ResponseEntity.ok(repository.findAll());
    }
}
