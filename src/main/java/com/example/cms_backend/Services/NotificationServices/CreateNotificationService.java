package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Repositories.NotificationRepository;

public class CreateNotificationService {
    private final NotificationRepository notificationRepository;

    public CreateNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
