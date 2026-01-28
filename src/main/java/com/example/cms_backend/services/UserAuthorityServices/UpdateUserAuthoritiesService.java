package com.example.cms_backend.services.UserAuthorityServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.AuthorityNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.UpdateUserAuthoritiesCommand;
import com.example.cms_backend.model.Entities.Authority;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.AuthorityRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UpdateUserAuthoritiesService implements Command<UpdateUserAuthoritiesCommand, String> {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UpdateUserAuthoritiesService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public ResponseEntity<String> execute(UpdateUserAuthoritiesCommand command) {
        Optional<User> userOptional = userRepository.findById(command.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();
        Set<Authority> newAuthorities = new HashSet<>();

        for (String authorityName : command.getAuthorityNames()) {
            Authority authority = authorityRepository.findByName(authorityName)
                    .orElseThrow(() -> new AuthorityNotFoundException());
            newAuthorities.add(authority);
        }

        user.setAuthorities(newAuthorities); // Completely replace old authorities
        userRepository.save(user);

        return ResponseEntity.ok("Authorities updated successfully!");
    }
}