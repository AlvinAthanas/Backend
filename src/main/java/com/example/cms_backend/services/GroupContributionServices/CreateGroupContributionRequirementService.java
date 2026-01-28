package com.example.cms_backend.services.GroupContributionServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.GroupNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.CreateGroupContributionRequirementCommand;
import com.example.cms_backend.model.DTO.GroupContributionRequirementDTO;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.GroupContributionRequirement;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGroupContributionRequirementService implements Command<CreateGroupContributionRequirementCommand, GroupContributionRequirementDTO> {

    private final GroupContributionRequirementRepository repository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<GroupContributionRequirementDTO> execute(CreateGroupContributionRequirementCommand command) {
        String email = LoggedInUserUtil.loggedInUserEmail(command.getRequest());
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        // Find user's community group
        Group communityGroup = user.getGroups()
                .stream()
                .filter(g -> g.getDescription().equalsIgnoreCase("community"))
                .findFirst()
                .orElseThrow(GroupNotFoundException::new);

        GroupContributionRequirement entity = new GroupContributionRequirement();
        entity.setGroupId(communityGroup.getId());
        entity.setContributionType(command.getContributionType());
        entity.setTargetAmount(command.getTargetAmount());
        entity.setDeadline(command.getDeadline());
        entity.setDescription(command.getDescription());
        entity.setDeclaredByUserId(user.getId());

        GroupContributionRequirement saved = repository.save(entity);

        GroupContributionRequirementDTO dto = new GroupContributionRequirementDTO(
                saved.getId(),
                saved.getGroupId(),
                communityGroup.getName(),
                saved.getContributionType(),
                saved.getTargetAmount(),
                saved.getDeadline(),
                saved.getDescription(),
                saved.getDeclaredByUserId()
        );

        return ResponseEntity.ok(dto);
    }
}

