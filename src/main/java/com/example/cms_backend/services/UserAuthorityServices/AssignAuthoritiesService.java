package com.example.cms_backend.services.UserAuthorityServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.AuthorityNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.AssignAuthorityCommand;
import com.example.cms_backend.model.Entities.Authority;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.AuthorityRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AssignAuthoritiesService implements Command<AssignAuthorityCommand, String> {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public AssignAuthoritiesService(AuthorityRepository authorityRepository,
                                  UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    public void AssignDefaultAuthority(User user) {
        // By default, new users shouldn't have any authorities
        if (user.getAuthorities() == null) {
            user.setAuthorities(new HashSet<>());
        }
    }

    @Override
    public ResponseEntity<String> execute(AssignAuthorityCommand command) {
        Optional<Authority> assignedAuthority = authorityRepository.findByName(command.getAuthorityName());
        Optional<User> userOptional = userRepository.findById(command.getId());
        if (assignedAuthority.isPresent()) {
            if (userOptional.isPresent()) {
                Authority authority = assignedAuthority.get();
                User user = userOptional.get();
                if (user.getAuthorities() == null) {
                    user.setAuthorities(new HashSet<>());
                }
                user.getAuthorities().add(authority);
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("Authority assigned: " + command.getAuthorityName());
            } else {
                throw new UserNotFoundException();
            }
        } else {
            throw new AuthorityNotFoundException();
        }
    }
}
