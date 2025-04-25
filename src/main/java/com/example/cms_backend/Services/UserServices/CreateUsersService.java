package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreateUsersService implements Command<List<User>,List<UserDTO>> {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CreateUsersService(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(List<User> users) {
        for (User user : users) {
            UserValidator.validateUser(user);
            if (!userRepository.existsByEmail(user.getEmail())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
            }
        }
        userRepository.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(users.stream().map(UserDTO::new).toList());
    }
}
