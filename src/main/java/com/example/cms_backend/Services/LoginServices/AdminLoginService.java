package com.example.cms_backend.Services.LoginServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.LoginResponse;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Security.Jwt.JwtUtil;
import com.example.cms_backend.Security.Jwt.UserLoginDTO;
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
            throw new RuntimeException("This portal is only for admin-level users.");
        }

        // If user is a Parishioner, ensure they're verified
        boolean isParishioner = user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("PARISHIONER"));

        if (isParishioner && user.getAdminVerificationStatus() != AdminVerificationStatus.VERIFIED) {
            throw new RuntimeException("PARISHIONER account is not yet verified.");
        }

        // If user is not a Parishioner, ensure parish has a verified Parishioner
        if (!isParishioner) {
            boolean parishHasVerifiedParishioner = userRepository.findAllByParishId(user.getParishId())
                    .stream()
                    .anyMatch(u ->
                            u.getAdminVerificationStatus() == AdminVerificationStatus.VERIFIED &&
                                    u.getRoles().stream()
                                            .anyMatch(role -> role.getName().equalsIgnoreCase("PARISHIONER"))
                    );

            if (!parishHasVerifiedParishioner) {
                throw new RuntimeException("Your parish does not yet have a verified PARISHIONER. Admin access is not allowed.");
            }
        }

        return ResponseEntity.ok(new LoginResponse(jwtToken, new UserDTO(user)));
    }
}
