package com.example.cms_backend.services.DioceseServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.DioceseNotFoundException;
import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.model.Commands.UpdateDioceseCommand;
import com.example.cms_backend.repositories.DioceseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateDioceseService implements Command<UpdateDioceseCommand, Diocese> {
    private final DioceseRepository dioceseRepository;

    public UpdateDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<Diocese> execute(UpdateDioceseCommand command) {
        Optional<Diocese> dioceseOptional = dioceseRepository.findById(command.getId());
        if (dioceseOptional.isPresent()) {
            Diocese diocese = command.getDiocese();
            diocese.setId(command.getId());
            dioceseRepository.save(diocese);
            return ResponseEntity.ok().body(diocese);
        }
        throw new DioceseNotFoundException();
    }
}
