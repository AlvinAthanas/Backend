package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.SubmitGroupContributionCommand;
import com.example.cms_backend.Model.DTO.GroupContributionSubmissionDTO;
import com.example.cms_backend.Model.Entities.GroupContributionSubmission;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmitGroupContributionService implements Command<SubmitGroupContributionCommand, GroupContributionSubmissionDTO> {

    private final GroupContributionSubmissionRepository submissionRepository;

    @Override
    public ResponseEntity<GroupContributionSubmissionDTO> execute(SubmitGroupContributionCommand command) {
        GroupContributionSubmission submission = new GroupContributionSubmission();
        submission.setRequirementId(command.getRequirementId());
        submission.setUserId(command.getUserId());
        submission.setAmount(command.getAmount());
        submission.setDate(command.getDate());
        submission.setNote(command.getNote());

        GroupContributionSubmission saved = submissionRepository.save(submission);

        GroupContributionSubmissionDTO dto = new GroupContributionSubmissionDTO(
                saved.getId(),
                saved.getRequirementId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getDate(),
                saved.getNote()
        );

        return ResponseEntity.ok(dto);
    }
}
