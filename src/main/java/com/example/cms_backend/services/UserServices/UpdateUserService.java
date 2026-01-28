package com.example.cms_backend.services.UserServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.ErrorMessages;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.exceptions.UserNotValidException;
import com.example.cms_backend.model.DTO.UpdateUserDTO;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Commands.UpdateUserCommand;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.validators.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements Command<UpdateUserCommand, UserDTO> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdateUserService(UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public ResponseEntity<UserDTO> execute(UpdateUserCommand command) {
        Long id = command.getId();
        UpdateUserDTO dto = command.getUpdateUserDTO();

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        // Update fields selectively
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setGender(dto.getGender());
        if (user.getParishId() == null || user.getParishId() == 0L) {
            user.setParishId(dto.getParishId());
        }

        if (!user.getEmail().equals(dto.getEmail())) {
            // email was changed — check if the new one already exists
            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
            }
            user.setEmail(dto.getEmail());
        }

        UserValidator.validateUser(user);
        userRepository.save(user);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

}

