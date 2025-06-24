package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.CreateGroupContributionRequirementCommand;
import com.example.cms_backend.Model.DTO.GroupContributionRequirementDTO;
import com.example.cms_backend.Model.Entities.GroupContributionRequirement;
import com.example.cms_backend.Repositories.GroupContributionRequirementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateGroupContributionRequirementService implements Command<CreateGroupContributionRequirementCommand, GroupContributionRequirementDTO> {

    private final GroupContributionRequirementRepository repository;

    @Override
    public ResponseEntity<GroupContributionRequirementDTO> execute(CreateGroupContributionRequirementCommand command) {
        GroupContributionRequirement entity = new GroupContributionRequirement();
        entity.setGroupId(command.getGroupId());
        entity.setContributionType(command.getContributionType());
        entity.setTargetAmount(command.getTargetAmount());
        entity.setDeadline(command.getDeadline());
        entity.setDescription(command.getDescription());
        entity.setDeclaredByUserId(command.getDeclaredByUserId());

        GroupContributionRequirement saved = repository.save(entity);

        GroupContributionRequirementDTO dto = new GroupContributionRequirementDTO(
                saved.getId(),
                saved.getGroupId(),
                saved.getContributionType(),
                saved.getTargetAmount(),
                saved.getDeadline(),
                saved.getDescription(),
                saved.getDeclaredByUserId()
        );

        return ResponseEntity.ok(dto);
    }
}