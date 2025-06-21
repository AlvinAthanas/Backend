package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.NotificationNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.UpdateNotificationCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.NotificationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateNotificationService implements Command<UpdateNotificationCommand, Notification> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UpdateNotificationService(NotificationRepository notificationRepository,
                                     UserRepository userRepository,
                                     GroupRepository groupRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Notification> execute(UpdateNotificationCommand command) {
        Optional<Notification> notificationOptional = notificationRepository.findById(command.getId());
        if (notificationOptional.isEmpty()) {
            throw new NotificationNotFoundException();
        }

        String email = LoggedInUserUtil.loggedInUserEmail(command.getRequest());
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Notification updated = command.getNotification();
        updated.setId(command.getId()); // Ensure we update the right notification
        updated.setSenderId(user.getId());
        updated.setParishId(user.getParishId());

        if (updated.getGroupId() != null) {
            groupRepository.findById(updated.getGroupId())
                    .ifPresent(group -> updated.setKandaId(group.getKandaId()));
        }

        if (updated.getUserId() != null) {
            userRepository.findById(updated.getUserId())
                    .ifPresent(foundUser-> updated.setUserId(foundUser.getId()));
        }

        notificationRepository.save(updated);
        return ResponseEntity.ok(updated);
    }
}
