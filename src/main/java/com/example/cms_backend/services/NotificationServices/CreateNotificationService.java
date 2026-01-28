package com.example.cms_backend.services.NotificationServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.CreateNotificationCommand;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.Notification;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.NotificationRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateNotificationService implements Command<CreateNotificationCommand, Notification> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public CreateNotificationService(NotificationRepository notificationRepository,
                                     UserRepository userRepository,
                                     GroupRepository groupRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Notification> execute(CreateNotificationCommand command) {
        Notification notification = command.getNotification();
        String email = LoggedInUserUtil.loggedInUserEmail(command.getRequest());

        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        // Set the logged-in user's parish ID to the notification
        notification.setSenderId(user.getId());
        notification.setParishId(user.getParishId());
        if (notification.getGroupId() != null) {
            Optional<Group> group = groupRepository.findById(notification.getGroupId());
            group.ifPresent(value -> notification.setKandaId(value.getKandaId()));
        }

        notificationRepository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
}
