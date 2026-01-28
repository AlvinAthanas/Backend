package com.example.cms_backend.services.GroupContributionServices;

import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.GroupContributionRequirementDTO;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetGroupContributionDeclarationsService {

    private final UserRepository userRepository;
    private final GroupContributionRequirementRepository requirementRepository;
    private final GroupContributionSubmissionRepository submissionRepository;

    public ResponseEntity<List<GroupContributionRequirementDTO>> execute(HttpServletRequest request, Boolean fulfilledFilter) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<Group> userGroups = user.getGroups();

        List<GroupContributionRequirementDTO> declarations = userGroups.stream()
                .flatMap(group -> requirementRepository.findByGroupId(group.getId()).stream()
                        .map(requirement -> {
                            Long total = submissionRepository.sumByRequirementId(requirement.getId()).orElse(0L);
                            boolean fulfilled = total >= requirement.getTargetAmount();

                            return new GroupContributionRequirementDTO(
                                    requirement.getId(),
                                    group.getId(),
                                    group.getName(),
                                    requirement.getContributionType(),
                                    requirement.getTargetAmount(),
                                    requirement.getDeadline(),
                                    requirement.getDescription(),
                                    requirement.getDeclaredByUserId(),
                                    total,
                                    fulfilled
                            );
                        }))
                .filter(dto -> fulfilledFilter == null || dto.isFulfilled() == fulfilledFilter)
                .collect(Collectors.toList());

        return ResponseEntity.ok(declarations);
    }
}
