package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.DTO.UpdateUserDTO;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Commands.UpdateUserCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Validators.UserValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
//        user.setParishId(dto.getParishId());
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

