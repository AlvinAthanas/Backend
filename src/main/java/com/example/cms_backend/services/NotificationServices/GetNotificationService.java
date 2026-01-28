package com.example.cms_backend.services.NotificationServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.NotificationNotFoundException;
import com.example.cms_backend.model.Entities.Notification;
import com.example.cms_backend.repositories.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetNotificationService implements Query<Long, Notification> {
    private final NotificationRepository notificationRepository;

    public GetNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public ResponseEntity<Notification> execute(Long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);
        if (notificationOptional.isPresent()) {
            return ResponseEntity.ok(notificationOptional.get());
        }
        throw new NotificationNotFoundException();
    }
}
