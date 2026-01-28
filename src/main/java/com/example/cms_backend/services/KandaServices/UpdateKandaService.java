package com.example.cms_backend.services.KandaServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.KandaNotFoundException;
import com.example.cms_backend.model.Commands.UpdateKandaCommand;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.repositories.KandaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateKandaService implements Command<UpdateKandaCommand, Kanda> {
    private final KandaRepository kandaRepository;

    public UpdateKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<Kanda> execute(UpdateKandaCommand command) {
        Optional<Kanda> kandaOptional = kandaRepository.findById(command.getId());
        if (kandaOptional.isPresent()) {
            Kanda kanda = command.getKanda();
            kanda.setId(command.getId());
            kandaRepository.save(kanda);
            return ResponseEntity.ok(kanda);
        }
        throw new KandaNotFoundException();
    }
}

