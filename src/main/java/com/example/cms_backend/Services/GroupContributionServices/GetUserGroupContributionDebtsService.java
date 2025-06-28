package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.GroupContributionDebtDTO;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.GroupContributionRequirement;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
