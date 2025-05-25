package com.example.cms_backend.Security.Jwt;

import com.example.cms_backend.Model.Commands.LoginResponse;
import com.example.cms_backend.Services.LoginServices.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost", allowedHeaders = {"Authorization", "Content-Type"})
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDTO loginDTO) {
        return loginService.execute(loginDTO);
    }
}
