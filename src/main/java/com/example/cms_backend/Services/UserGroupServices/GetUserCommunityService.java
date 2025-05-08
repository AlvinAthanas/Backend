package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.GroupDTO;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
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
