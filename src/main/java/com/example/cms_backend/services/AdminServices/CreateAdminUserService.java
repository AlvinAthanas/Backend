package com.example.cms_backend.services.AdminServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.ErrorMessages;
import com.example.cms_backend.exceptions.UserNotValidException;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.AdminVerificationKey;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Enums.AdminVerificationStatus;
import com.example.cms_backend.repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.services.UserRoleServices.AssignRolesService;
import com.example.cms_backend.validators.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CreateAdminUserService implements Command<User, UserDTO> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AssignRolesService assignRolesService;
    private final AdminVerificationKeyRepository keyRepository;

    public CreateAdminUserService(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  AssignRolesService assignRolesService,
                                  AdminVerificationKeyRepository keyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.assignRolesService = assignRolesService;
        this.keyRepository = keyRepository;
    }

    @Override
    public ResponseEntity<UserDTO> execute(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserNotValidException(ErrorMessages.EMAIL_ALREADY_EXISTS.getMessage());
        }

        // ✅ Step 1: Ensure parishId is present
        if (user.getParishId() == null) {
            throw new UserNotValidException("Parish ID is required to register as a PARISHIONER.");
        }

        // ✅ Step 2: Get all users in this parish
        List<User> usersInParish = userRepository.findAllByParishId(user.getParishId());

        // ✅ Step 3: Check if any VERIFIED PARISHIONER exists in this parish
        boolean verifiedParishionerExists = usersInParish.stream()
                .filter(u -> u.getAdminVerificationStatus() == AdminVerificationStatus.VERIFIED)
                .filter(u -> u.getRoles() != null)
                .flatMap(u -> u.getRoles().stream())
                .anyMatch(role -> role.getName().equalsIgnoreCase("PARISHIONER"));


        if (verifiedParishionerExists) {
            throw new UserNotValidException("This parish already has a registered PARISHIONER.");
        }

        // ✅ Proceed with registration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAdminVerificationStatus(AdminVerificationStatus.NOT_VERIFIED);

        UserValidator.validateUser(user);
        user = userRepository.save(user);

        assignRolesService.assignAdminRole(user);

        String key = UUID.randomUUID().toString();
        AdminVerificationKey verificationKey = new AdminVerificationKey();
        verificationKey.setKey(key);
        verificationKey.setUser(user);
        verificationKey.setUsed(false);
        verificationKey.setCreatedAt(LocalDateTime.now());

        keyRepository.save(verificationKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

}
