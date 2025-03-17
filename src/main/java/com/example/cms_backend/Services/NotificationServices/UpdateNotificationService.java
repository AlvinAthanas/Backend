package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateNotificationService {
    private final NotificationRepository notificationRepository;

    public UpdateNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
