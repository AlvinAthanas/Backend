package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.NotificationNotFoundException;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Commands.UpdateNotificationCommand;
import com.example.cms_backend.Repositories.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateNotificationService implements Command<UpdateNotificationCommand, Notification> {
    private final NotificationRepository notificationRepository;

    public UpdateNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    public ResponseEntity<Notification> execute(UpdateNotificationCommand command) {
        Optional<Notification> notificationOptional = notificationRepository.findById(command.getId());
        if (notificationOptional.isPresent()) {
            Notification notification = command.getNotification();
            notification.setId(command.getId());
            notificationRepository.save(notification);
            return ResponseEntity.ok(notification);
        }
        throw new NotificationNotFoundException();
    }
}
