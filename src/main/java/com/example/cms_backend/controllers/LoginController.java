package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.LoginResponse;
import com.example.cms_backend.security.Jwt.UserLoginDTO;
import com.example.cms_backend.services.LoginServices.AdminLoginService;
import com.example.cms_backend.services.LoginServices.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost", allowedHeaders = {"Authorization", "Content-Type"})
public class LoginController {

    private final LoginService loginService;
    private final AdminLoginService adminLoginService;

    public LoginController(LoginService loginService,
                           AdminLoginService adminLoginService) {
        this.loginService = loginService;
        this.adminLoginService = adminLoginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDTO loginDTO) {
        return loginService.execute(loginDTO);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> loginAdmin(@RequestBody UserLoginDTO loginDTO) {
        return adminLoginService.execute(loginDTO);
    }
}
