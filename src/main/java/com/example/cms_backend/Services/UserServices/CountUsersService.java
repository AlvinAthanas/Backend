package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountUsersService implements Query<Void,Long> {
    private final UserRepository userRepository;

    public CountUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(Void input) {
        return ResponseEntity.ok(userRepository.count());
    }
}
