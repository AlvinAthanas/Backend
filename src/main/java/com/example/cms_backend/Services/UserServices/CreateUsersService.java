package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreateUsersService implements Command<List<User>,List<UserDTO>> {

    private final UserRepository userRepository;

    public CreateUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(List<User> users) {
        userRepository.saveAll(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(users.stream().map(UserDTO::new).toList());
    }
}
