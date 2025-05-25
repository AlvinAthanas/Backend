package com.example.cms_backend.Services.LoginServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.LoginResponse;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Security.Jwt.JwtUtil;
import com.example.cms_backend.Security.Jwt.UserLoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements Command<UserLoginDTO, LoginResponse> {

    private final AuthenticationManager manager;
    private final UserRepository userRepository;

    public LoginService(AuthenticationManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<LoginResponse> execute(UserLoginDTO input) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                input.getEmail(), input.getPassword()
        );

        Authentication authentication = manager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtUtil.generateToken((User) authentication.getPrincipal());

        Optional<com.example.cms_backend.Model.Entities.User> user = userRepository.findByEmail(input.getEmail());
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(new LoginResponse(jwtToken, userDTO));
    }
}
