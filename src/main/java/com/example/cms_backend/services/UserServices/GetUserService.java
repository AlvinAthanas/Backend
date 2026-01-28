package com.example.cms_backend.services.UserServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.GetUserCommand;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserService implements Query<GetUserCommand, UserDTO> {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserDTO> execute(GetUserCommand command) {
        Optional<User> userOptional = Optional.empty();

        if (command.getId() != null) {
            userOptional = userRepository.findById(command.getId());
        } else if (command.getEmail() != null) {
            userOptional = userRepository.findByEmail(command.getEmail());
        }

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(new UserDTO(userOptional.get()));
        }

        throw new UserNotFoundException();
    }
}

