package com.example.cms_backend.services.JoinCommunityRequestServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.JoinCommunityRequestCommand;
import com.example.cms_backend.model.DTO.JoinCommunityRequestDTO;
import com.example.cms_backend.model.Entities.JoinCommunityRequest;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Enums.RequestStatus;
import com.example.cms_backend.repositories.JoinCommunityRequestRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateJoinCommunityRequestService implements Command<JoinCommunityRequestCommand, JoinCommunityRequestDTO> {

    private final JoinCommunityRequestRepository repository;
    private final UserRepository userRepository;

    public CreateJoinCommunityRequestService(JoinCommunityRequestRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<JoinCommunityRequestDTO> execute(JoinCommunityRequestCommand command) {
        User user = userRepository.findById(command.getUserId()).orElseThrow(UserNotFoundException::new);

        JoinCommunityRequest request = new JoinCommunityRequest();
        request.setUserId(user.getId());
        request.setGroupId(command.getGroupId());
        request.setStatus(RequestStatus.PENDING);
        request.setSubmittedAt(LocalDateTime.now());

        repository.save(request);

        return ResponseEntity.ok(JoinCommunityRequestDTO.from(request));
    }
}

