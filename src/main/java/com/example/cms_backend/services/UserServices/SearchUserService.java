package com.example.cms_backend.services.UserServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.SearchUserCommand;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchUserService implements Query<SearchUserCommand, List<UserDTO>> {

    private final UserRepository userRepository;

    public SearchUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(SearchUserCommand command) {
        List<User> users = userRepository.findByNameContaining(command.getName());

        HttpServletRequest request = command.getRequest();
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        users = users.stream()
                                .filter(user -> parishId.equals(user.getParishId()))
                                .toList();
                    }
                }
            }
        }

        return ResponseEntity.ok(users.stream()
                .map(UserDTO::new)
                .toList());
    }
}
