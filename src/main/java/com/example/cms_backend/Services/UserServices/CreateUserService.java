package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ErrorMessages;
import com.example.cms_backend.Exceptions.UserNotValidException;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserRoleServices.AssignRolesService;
import com.example.cms_backend.Validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
        }
        UserValidator.validateUser(user);
        assignRolesService.AssignDefaultRole(user);
        System.out.println("User roles before save: " + user.getRoles());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }
}
