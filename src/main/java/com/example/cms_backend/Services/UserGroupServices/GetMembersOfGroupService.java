package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
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

