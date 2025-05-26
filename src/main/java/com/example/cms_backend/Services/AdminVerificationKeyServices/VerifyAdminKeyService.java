package com.example.cms_backend.Services.AdminVerificationKeyServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.VerifyAdminKeyCommand;
import com.example.cms_backend.Model.Entities.AdminVerificationKey;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification key.");
        }

        AdminVerificationKey key = optionalKey.get();

        // Check if key is already used
        if (key.isUsed()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This key has already been used.");
        }

        // Check if the key belongs to this user
        if (!key.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This key does not belong to this user.");
        }

        // All checks passed, verify user
        user.setAdminVerificationStatus(AdminVerificationStatus.VERIFIED);
        userRepo.save(user);

        // Mark key as used
        key.setUsed(true);
        keyRepo.save(key);

        return ResponseEntity.ok("Admin verified successfully.");
    }

}
