package com.example.cms_backend.Services.UserAuthorityServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.AuthorityNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand;
import com.example.cms_backend.Model.Entities.Authority;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.AuthorityRepository;
import com.example.cms_backend.Repositories.UserRepository;
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