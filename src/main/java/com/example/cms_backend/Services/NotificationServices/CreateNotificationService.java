package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.CreateNotificationCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.NotificationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
