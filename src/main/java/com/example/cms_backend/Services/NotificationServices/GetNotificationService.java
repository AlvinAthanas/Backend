package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Repositories.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class GetNotificationService {
    private final NotificationRepository notificationRepository;

    public GetNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
}
