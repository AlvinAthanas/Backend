package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchUserService implements Query<String, List<UserDTO>> {

    private final UserRepository userRepository;

    public SearchUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(String name) {

        return ResponseEntity.ok(userRepository.findByNameContaining(name)
                .stream()
                .map(UserDTO::new)
                .toList());
    }
}
