package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.GroupDTO;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserCommunityService implements Query<Long, GroupDTO> {

    private final UserRepository userRepository;

    public GetUserCommunityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<GroupDTO> execute(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException();

        Optional<Group> community = user.get().getGroups().stream()
                .filter(g -> "community".equalsIgnoreCase(g.getDescription()))
                .findFirst();

        return community
                .map(g -> ResponseEntity.ok(new GroupDTO(g.getId(), g.getName(), g.getDescription())))
                .orElse(ResponseEntity.notFound().build());
    }
}
