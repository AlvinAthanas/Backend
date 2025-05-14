package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.UploadProfilePictureCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UploadProfilePictureService implements Command<UploadProfilePictureCommand, Void> {

    private final UserRepository userRepository;

    public UploadProfilePictureService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Void> execute(UploadProfilePictureCommand command) {
        Optional<User> optionalUser = userRepository.findById(command.getUserId());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        try {
            user.setProfilePicture(command.getFile().getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
