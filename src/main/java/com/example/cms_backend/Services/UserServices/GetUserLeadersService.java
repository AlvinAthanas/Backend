package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.UserLeaderDTO;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Repositories.RoleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetUserLeadersService implements Query<HttpServletRequest, List<UserLeaderDTO>> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<List<UserLeaderDTO>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        Long parishId = currentUser.getParishId();
        Set<Group> userGroups = currentUser.getGroups();

        // 🔧 Convert enums to Set<String> role names
        Set<String> parishRoleNames = Set.of(
                Roles.PARISHIONER,
                Roles.COMMITTEE_CHAIRPERSON,
                Roles.COMMITTEE_SECRETARY,
                Roles.COMMITTEE_TREASURER,
                Roles.CATECHIST
        ).stream().map(Roles::toString).collect(Collectors.toSet());

        Set<String> communityRoleNames = Set.of(
                Roles.COMMITTEE_CHAIRPERSON,
                Roles.COMMITTEE_SECRETARY,
                Roles.COMMITTEE_TREASURER
        ).stream().map(Roles::toString).collect(Collectors.toSet());

        Set<Role> parishRoles = roleRepository.findByNameIn(parishRoleNames);
        Set<Role> communityRoles = roleRepository.findByNameIn(communityRoleNames);

        Set<UserLeaderDTO> leaders = new HashSet<>();

        // ✅ Parish-level leaders
        List<User> parishLeaders = userRepository.findByParishIdAndRolesIn(parishId, parishRoles);
        for (User user : parishLeaders) {
            for (Role role : user.getRoles()) {
                if (parishRoles.contains(role)) {
                    leaders.add(new UserLeaderDTO(user.getId(), user.getName(), role.getName()));
                }
            }
        }

        // ✅ Community-level leaders from groups with description = "community"
        for (Group group : userGroups) {
            if (!"community".equalsIgnoreCase(group.getDescription())) continue;

            for (User groupMember : group.getUsers()) {
                for (Role role : groupMember.getRoles()) {
                    if (communityRoles.contains(role)) {
                        leaders.add(new UserLeaderDTO(groupMember.getId(), groupMember.getName(), role.getName()));
                    }
                }
            }
        }

        return ResponseEntity.ok(new ArrayList<>(leaders));
    }
}

