package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.UpdateCommands.UpdateUserCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Validators.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateUserService implements Command<UpdateUserCommand, UserDTO> {
    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<UserDTO> execute(UpdateUserCommand command) {
        Long id = command.getId();
        Optional<User> userOptonal = userRepository.findById(id);
        if (userOptonal.isPresent()) {
            User user = command.getUser();
            user.setId(id);
            UserValidator.validateUser(user);
            userRepository.save(user);
            return ResponseEntity.ok().body(new UserDTO(user));
        }
        throw new UserNotFoundException();
    }
}

