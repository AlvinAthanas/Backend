package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
