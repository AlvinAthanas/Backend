package com.example.cms_backend.services.UserRoleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetUserRolesService implements Query<Long, Set<String>> {
    private final UserRepository userRepository;

    public GetUserRolesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Set<String>> execute(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if (!user.isPresent()) {
//            Set<U>
//        }
        return ResponseEntity.ok(
                userRepository.findById(id)
                        .map(user -> user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()))
                        .orElse(Collections.emptySet())
        );
    }
}
