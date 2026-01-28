package com.example.cms_backend.services.NotificationServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.NotificationNotFoundException;
import com.example.cms_backend.model.Entities.Notification;
import com.example.cms_backend.repositories.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteNotificationService implements Command<Long,Void> {
    private final NotificationRepository notificationRepository;

    public DeleteNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            notificationRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new NotificationNotFoundException();
    }
}
