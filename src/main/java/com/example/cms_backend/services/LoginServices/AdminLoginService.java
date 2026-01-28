package com.example.cms_backend.services.LoginServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.AdminNotValidException;
import com.example.cms_backend.exceptions.ErrorMessages;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.LoginResponse;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Enums.AdminVerificationStatus;
import com.example.cms_backend.model.Enums.Roles;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.security.Jwt.JwtUtil;
import com.example.cms_backend.security.Jwt.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService implements Command<UserLoginDTO, LoginResponse> {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;

    public AdminLoginService(AuthenticationManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<LoginResponse> execute(UserLoginDTO input) {
        // Authenticate credentials
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwtToken = JwtUtil.generateToken((UserDetails) authentication.getPrincipal());

        // Fetch user entity
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // Check if user has any elevated roles (not just MEMBER)
        boolean isOnlyMember = user.getRoles().stream()
                .allMatch(role -> role.getName().equalsIgnoreCase("PARISH_MEMBER"));

        if (isOnlyMember) {
            throw new AdminNotValidException(ErrorMessages.ONLY_FOR_ADMINS.getMessage());
        }

        // If user is a Parishioner, ensure they're verified
        boolean isParishioner = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("PARISHIONER"));

        if (isParishioner && user.getAdminVerificationStatus() != AdminVerificationStatus.VERIFIED) {
            throw new AdminNotValidException(ErrorMessages.ACCOUNT_NOT_VERIFIED.getMessage());
        }

        // If user is not a Parishioner, ensure parish has a verified Parishioner
        if (!isParishioner) {
            boolean parishHasVerifiedParishioner = userRepository.findAllByParishId(user.getParishId())
                    .stream()
                    .anyMatch(u ->
                            u.getAdminVerificationStatus() == AdminVerificationStatus.VERIFIED &&
                                    u.getRoles().stream()
                                            .anyMatch(role -> role.getName().equalsIgnoreCase(Roles.PARISHIONER.toString()))
                    );

            if (!parishHasVerifiedParishioner) {
                throw new AdminNotValidException(ErrorMessages.NO_ADMIN_IN_PARISH.getMessage());
            }
        }

        return ResponseEntity.ok(new LoginResponse(jwtToken, new UserDTO(user)));
    }
}
