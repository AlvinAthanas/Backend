package com.example.cms_backend.Services.UserAuthorityServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetUserAuthoritiesService implements Query<Long, Set<String>> {
    private final UserRepository userRepository;

    public GetUserAuthoritiesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Set<String>> execute(Long id) {
        return ResponseEntity.ok(
                userRepository.findById(id)
                        .map(user -> user.getAuthorities().stream().map(authority -> authority.getName()).collect(Collectors.toSet()))
                        .orElse(Collections.emptySet())
        );
    }
}