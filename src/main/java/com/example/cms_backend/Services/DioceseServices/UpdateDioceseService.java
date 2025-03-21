package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.DioceseNotFoundException;
import com.example.cms_backend.Model.Entities.Diocese;
import com.example.cms_backend.Model.UpdateCommands.UpdateDioceseCommand;
import com.example.cms_backend.Repositories.DioceseRepository;
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
