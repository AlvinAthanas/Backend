package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Commands.UpdateNotificationCommand;
import com.example.cms_backend.Services.NotificationServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {
    private final CreateNotificationService createNotificationService;
    private final DeleteNotificationService deleteNotificationService;
    private final UpdateNotificationService updateNotificationService;
    private final GetNotificationService getNotificationService;
    private final GetNotificationsService getNotificationsService;

    public NotificationController(CreateNotificationService createNotificationService, DeleteNotificationService deleteNotificationService, UpdateNotificationService updateNotificationService, GetNotificationService getNotificationService, GetNotificationsService getNotificationsService) {
        this.createNotificationService = createNotificationService;
        this.deleteNotificationService = deleteNotificationService;
        this.updateNotificationService = updateNotificationService;
        this.getNotificationService = getNotificationService;
        this.getNotificationsService = getNotificationsService;
    }

    @PostMapping("/notification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return createNotificationService.execute(notification);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        return getNotificationService.execute(id);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications() {
        return getNotificationsService.execute(null);
    }

    @PutMapping("/notification/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification) {
        return updateNotificationService.execute(new UpdateNotificationCommand(id, notification));
    }

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        return deleteNotificationService.execute(id);
    }
}


