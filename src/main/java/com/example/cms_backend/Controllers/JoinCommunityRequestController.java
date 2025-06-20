package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.JoinCommunityRequestCommand;
import com.example.cms_backend.Model.DTO.JoinCommunityRequestDTO;
import com.example.cms_backend.Services.JoinCommunityRequestServices.CreateJoinCommunityRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinCommunityRequestController {

    private final CreateJoinCommunityRequestService createService;

    public JoinCommunityRequestController(CreateJoinCommunityRequestService createService) {
        this.createService = createService;
    }

    @PostMapping("/join-community-request")
    public ResponseEntity<JoinCommunityRequestDTO> submitRequest(@RequestBody JoinCommunityRequestCommand command) {
        return createService.execute(command);
    }
}

