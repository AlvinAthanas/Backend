package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.GroupContributionMemberProgressDTO;
import com.example.cms_backend.Model.Entities.GroupContributionRequirement;
import com.example.cms_backend.Model.Entities.GroupContributionSubmission;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetGroupContributionMemberProgressService implements Query<Long, List<GroupContributionMemberProgressDTO>> {

    private final GroupContributionRequirementRepository requirementRepository;
    private final GroupContributionSubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<GroupContributionMemberProgressDTO>> execute(Long requirementId) {
        GroupContributionRequirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new RuntimeException("Requirement not found"));

        Long groupId = requirement.getGroupId();

        // Get all users in this group
        List<User> groupMembers = userRepository.findUsersByGroupId(groupId);

        // Get all submissions for this requirement
        List<GroupContributionSubmission> submissions = submissionRepository.findByRequirementId(requirementId);

        // Map userId -> total contributed
        Map<Long, Long> userTotalMap = submissions.stream()
                .collect(Collectors.groupingBy(
                        GroupContributionSubmission::getUserId,
                        Collectors.summingLong(GroupContributionSubmission::getAmount)
                ));

        // Build DTO for each group member
        List<GroupContributionMemberProgressDTO> dtos = groupMembers.stream()
                .map(user -> new GroupContributionMemberProgressDTO(
                        user.getId(),
                        user.getName(),
                        userTotalMap.getOrDefault(user.getId(), 0L)
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
