package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.GroupContributionSubmissionDTO;
import com.example.cms_backend.Model.Entities.GroupContributionRequirement;
import com.example.cms_backend.Model.Entities.GroupContributionSubmission;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetMyGroupContributionSubmissionsService implements Query<HttpServletRequest, List<GroupContributionSubmissionDTO>> {

    private final GroupContributionSubmissionRepository submissionRepository;
    private final GroupContributionRequirementRepository requirementRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<GroupContributionSubmissionDTO>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        List<GroupContributionSubmissionDTO> dtos = submissionRepository.findByUserId(user.getId())
                .stream()
                .map(sub -> {
                    GroupContributionRequirement requirement = requirementRepository.findById(sub.getRequirementId()).orElse(null);

                    return new GroupContributionSubmissionDTO(
                            sub.getId(),
                            sub.getRequirementId(),
                            sub.getUserId(),
                            sub.getAmount(),
                            sub.getDate(),
                            sub.getNote(),
                            user.getName(),
                            requirement != null ? requirement.getContributionType() : "Unknown"
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
