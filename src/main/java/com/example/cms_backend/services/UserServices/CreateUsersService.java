package com.example.cms_backend.services.UserServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.ErrorMessages;
import com.example.cms_backend.exceptions.UserNotValidException;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import com.example.cms_backend.validators.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return execute(users, null);
    }

    public ResponseEntity<List<UserDTO>> execute(List<User> users, HttpServletRequest request) {
        // Get logged-in user's parishId if available
        Long loggedInUserParishId = null;
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    loggedInUserParishId = loggedInUser.getParishId();
                }
            }
        }

        for (User user : users) {
            UserValidator.validateUser(user);
            if (!userRepository.existsByEmail(user.getEmail())) {
                user.setPassword(passwordEncoder.encode(user.getEmail()));

                // Set parishId if it's null and we have a logged-in user's parishId
                if (user.getParishId() == null && loggedInUserParishId != null) {
                    user.setParishId(loggedInUserParishId);
                }
            } else {
                throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
            }
        }
        userRepository.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(users.stream().map(UserDTO::new).toList());
    }
}
