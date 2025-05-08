package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.GroupDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
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
