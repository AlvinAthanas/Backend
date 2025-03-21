package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ParishNotFoundException;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteParishService implements Command<Long,Void> {
    private final ParishRepository parishRepository;

    public DeleteParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Parish> parishOptional = parishRepository.findById(id);
        if (parishOptional.isPresent()) {
            parishRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ParishNotFoundException();
    }
}
