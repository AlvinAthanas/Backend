package com.example.cms_backend.services.GroupContributionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.GroupContributionSubmissionDTO;
import com.example.cms_backend.model.Entities.GroupContributionRequirement;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
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
