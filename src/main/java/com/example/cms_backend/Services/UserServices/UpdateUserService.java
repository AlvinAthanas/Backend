package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService {
    private final UserRepository userRepository;

    public UpdateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
