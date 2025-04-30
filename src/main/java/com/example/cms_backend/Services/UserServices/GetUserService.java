package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.GetUserCommand;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
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

