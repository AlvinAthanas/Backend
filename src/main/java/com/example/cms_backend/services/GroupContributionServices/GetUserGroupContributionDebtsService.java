package com.example.cms_backend.services.GroupContributionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.GroupContributionDebtDTO;
import com.example.cms_backend.model.Entities.Group;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetUserGroupContributionDebtsService implements Query<HttpServletRequest, List<GroupContributionDebtDTO>> {

    private final UserRepository userRepository;
    private final GroupContributionRequirementRepository requirementRepository;
    private final GroupContributionSubmissionRepository submissionRepository;

    @Override
    public ResponseEntity<List<GroupContributionDebtDTO>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<Group> userGroups = user.getGroups();

        List<GroupContributionDebtDTO> debts = new ArrayList<>();

        for (Group group : userGroups) {
            List<GroupContributionRequirement> requirements = requirementRepository.findByGroupId(group.getId());

            for (GroupContributionRequirement requirement : requirements) {
                Long targetAmount = requirement.getTargetAmount();

                // Total collected by the group
                Long totalCollected = submissionRepository.sumByRequirementId(requirement.getId()).orElse(0L);
                LocalDate deadline = requirement.getDeadline();
                Long groupRemaining = targetAmount - totalCollected;

                // Number of users in the group
                Long groupSize = userRepository.countUsersInGroup(group.getId());

                // User's total contribution towards this requirement
                Long userContributed = submissionRepository.sumUserContribution(requirement.getId(), user.getId()).orElse(0L);

                // Personal share (target / number of users)
                Long personalShare = groupSize > 0 ? targetAmount / groupSize : 0L;

                Long userRemaining = personalShare - userContributed;

                if (userRemaining > 0) {
                    debts.add(new GroupContributionDebtDTO(
                            requirement.getId(),
                            requirement.getContributionType(),
                            group.getName(),
                            targetAmount,
                            totalCollected,
                            groupRemaining,
                            groupSize,
                            personalShare,
                            userContributed,
                            userRemaining,
                            deadline
                    ));
                }
            }
        }

        return ResponseEntity.ok(debts);
    }
}
