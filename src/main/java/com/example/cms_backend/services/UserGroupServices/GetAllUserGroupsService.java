package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.GroupDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetAllUserGroupsService implements Query<Long, List<GroupDTO>> {

    private final UserRepository userRepository;

    public GetAllUserGroupsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<GroupDTO>> execute(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException();

        List<GroupDTO> groups = user.get().getGroups().stream()
                .map(group -> new GroupDTO(group.getId(), group.getName(), group.getDescription()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(groups);
    }
}
