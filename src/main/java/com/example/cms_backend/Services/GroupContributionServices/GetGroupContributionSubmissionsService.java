package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.GroupContributionSubmissionViewDTO;
import com.example.cms_backend.Model.Entities.GroupContributionSubmission;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetGroupContributionSubmissionsService implements Query<Long, List<GroupContributionSubmissionViewDTO>> {

    private final GroupContributionSubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<GroupContributionSubmissionViewDTO>> execute(Long requirementId) {
        List<GroupContributionSubmission> submissions = submissionRepository.findByRequirementId(requirementId);

        List<GroupContributionSubmissionViewDTO> dtos = submissions.stream().map(sub -> {
            User user = userRepository.findById(sub.getUserId()).orElse(null);
            String userName = (user != null) ? user.getName() : "Unknown";

            return new GroupContributionSubmissionViewDTO(
                    sub.getId(),
                    sub.getRequirementId(),
                    sub.getUserId(),
                    userName,
                    sub.getAmount(),
                    sub.getDate(),
                    sub.getNote()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
