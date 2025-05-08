package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.Commands.ChangePasswordCommand;
import com.example.cms_backend.Model.DTO.ChangePasswordDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService implements Command<ChangePasswordCommand, Void> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Void> execute(ChangePasswordCommand command) {
        Long userId = command.getUserId();
        ChangePasswordDTO dto = command.getPasswordDTO();

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // Match current password
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new UserNotValidException(ErrorMessages.PASSWORD_INCORRECT.getMessage());
        }

        // Encode and save new password
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
