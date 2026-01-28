package com.example.cms_backend.services.NotificationServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.DTO.NotificationViewDTO;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.model.Entities.Notification;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.KandaRepository;
import com.example.cms_backend.repositories.NotificationRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetNotificationsService implements Query<HttpServletRequest, List<NotificationViewDTO>> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final KandaRepository kandaRepository;

    @Override
    public ResponseEntity<List<NotificationViewDTO>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();

        Set<Group> userGroups = user.getGroups();
        Set<Long> groupIds = userGroups.stream().map(Group::getId).collect(Collectors.toSet());
        Set<Long> kandaIds = userGroups.stream().map(Group::getKandaId).collect(Collectors.toSet());
        Long parishId = user.getParishId();

        List<Notification> notifications = notificationRepository.findScopedNotifications(
                groupIds, kandaIds, parishId, user.getId()
        );

        List<NotificationViewDTO> dtos = notifications.stream().map(notification -> {
            String targetGroupName = null;
            String targetKandaName = null;

            if (notification.getGroupId() != null) {
                targetGroupName = groupRepository.findById(notification.getGroupId())
                        .map(Group::getName)
                        .orElse(null);
            }

            if (notification.getKandaId() != null) {
                targetKandaName = kandaRepository.findById(notification.getKandaId())
                        .map(Kanda::getName)  // Assuming Kanda entity has getName()
                        .orElse(null);
            }

            return new NotificationViewDTO(
                    notification.getId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getDate(),
                    notification.getIsGlobal(),
                    notification.getUserId(),
                    notification.getSenderId(),
                    notification.getGroupId(),
                    targetGroupName,
                    notification.getKandaId(),
                    targetKandaName,
                    notification.getParishId()
            );
        }).toList();

        return ResponseEntity.ok(dtos);
    }
}
