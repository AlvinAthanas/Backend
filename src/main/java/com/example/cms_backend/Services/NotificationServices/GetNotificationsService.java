package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.NotificationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Security.Jwt.JwtUtil;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetNotificationsService implements Query<HttpServletRequest, List<Notification>> {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public GetNotificationsService(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Notification>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            System.out.println("User Not Found");
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        Set<Group> groups = user.getGroups();

        Long userId = user.getId();
        Set<Long> userGroupIds = groups.stream().map(Group::getId).collect(Collectors.toSet());
        Set<Long> userKandaIds = groups.stream().map(Group::getKandaId).collect(Collectors.toSet());

        // Get relevant notifications
        List<Notification> notifications = notificationRepository.findRelevantNotifications(
                userGroupIds, userKandaIds, userId
        );

        return ResponseEntity.ok(notifications);
    }

}
