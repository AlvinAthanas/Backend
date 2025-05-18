package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserRoleServices.AssignRolesService;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import com.example.cms_backend.Validators.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateUserService implements Command<User, UserDTO>{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private AssignRolesService assignRolesService;

    public CreateUserService(UserRepository userRepository,
                             PasswordEncoder passwordEncoder,
                             AssignRolesService assignRolesService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.assignRolesService = assignRolesService;
    }

    @Override
    public ResponseEntity<UserDTO> execute(User user) {
        return execute(user, null);
    }

    public ResponseEntity<UserDTO> execute(User user, HttpServletRequest request) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
        }

        // Check if parishId is null and set it from logged-in user if available
        if (user.getParishId() == null && request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    user.setParishId(loggedInUser.getParishId());
                }
            }
            // If no logged-in user or logged-in user has no parishId, leave parishId as null
        }

        UserValidator.validateUser(user);

        // First, save the user to generate an ID
        user = userRepository.save(user);
        // Then, assign the default role to the user
        assignRolesService.AssignDefaultRole(user);
        // Finally, save the user again to update the role ID
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }
}
