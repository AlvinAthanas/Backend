package com.example.cms_backend.services.AdminVerificationKeyServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.InvalidKeyException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.VerifyAdminKeyCommand;
import com.example.cms_backend.model.Entities.AdminVerificationKey;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Enums.AdminVerificationStatus;
import com.example.cms_backend.repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerifyAdminKeyService implements Command<VerifyAdminKeyCommand, String> {

    private final AdminVerificationKeyRepository keyRepo;
    private final UserRepository userRepo;

    public VerifyAdminKeyService(AdminVerificationKeyRepository keyRepo, UserRepository userRepo) {
        this.keyRepo = keyRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ResponseEntity<String> execute(VerifyAdminKeyCommand command) {
        // First, find the user by email
        User user = userRepo.findByEmail(command.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // Then, fetch the key
        Optional<AdminVerificationKey> optionalKey = keyRepo.findByKey(command.getKey().trim());

        if (optionalKey.isEmpty()) {
            throw new InvalidKeyException("The key you entered is not valid. Please try again.");
        }

        AdminVerificationKey key = optionalKey.get();

        // Check if key is already used
        if (key.isUsed()) {
            throw new InvalidKeyException("This key has already been used. Please try again.");
        }

        // Check if the key belongs to this user
        if (!key.getUser().getId().equals(user.getId())) {
            throw new InvalidKeyException("This key does not belong to another user. Please try again.");
        }

        // All checks passed, verify user
        user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
        userRepo.save(user);

        // Mark key as used
        key.setUsed(true);
        keyRepo.save(key);

        return ResponseEntity.ok("You were verified successfully.");
    }

}
