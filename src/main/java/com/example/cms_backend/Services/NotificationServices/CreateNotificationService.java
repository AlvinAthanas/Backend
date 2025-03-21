package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Repositories.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateNotificationService implements Command<Notification,Notification> {
    private final NotificationRepository notificationRepository;

    public CreateNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ResponseEntity<Notification> execute(Notification notification) {
        notificationRepository.save(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(notification);
    }
}
