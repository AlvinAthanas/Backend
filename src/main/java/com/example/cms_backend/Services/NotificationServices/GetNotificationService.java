package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.NotificationNotFoundException;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Repositories.NotificationRepository;
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
