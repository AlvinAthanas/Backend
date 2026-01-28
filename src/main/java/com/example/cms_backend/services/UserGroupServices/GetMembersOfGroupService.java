package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMembersOfGroupService implements Query<GroupNameDescriptionCommand, List<UserDTO>> {
    private final UserRepository userRepository;

    public GetMembersOfGroupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<UserDTO>> execute(GroupNameDescriptionCommand command) {
        List<User> users = userRepository.findUsersByGroupDescriptionAndName(command.getDescription(), command.getGroupName());
        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(userDTOs);
    }
}

