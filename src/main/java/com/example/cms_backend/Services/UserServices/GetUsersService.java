package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetUsersService implements Query<Void, List<UserDTO>> {
    private final UserRepository userRepository;

    public GetUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(Void input) {
        return execute(input, null);
    }

    public ResponseEntity<List<UserDTO>> execute(Void input, HttpServletRequest request) {
        List<User> users = userRepository.findAll();

        // Filter users by parishId if request is provided
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        // Filter users by parishId
                        users = users.stream()
                                .filter(user -> parishId.equals(user.getParishId()))
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        List<UserDTO> userDTOS = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(userDTOS);
    }
}
