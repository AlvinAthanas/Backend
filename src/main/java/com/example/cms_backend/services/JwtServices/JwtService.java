package com.example.cms_backend.services.JwtServices;


import com.example.cms_backend.security.Jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Invalid Authorization header");
    }

    public String extractEmailFromRequest(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        return JwtUtil.extractUsername(token);
    }
}
