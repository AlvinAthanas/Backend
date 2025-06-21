package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.CreateNotificationCommand;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Commands.UpdateNotificationCommand;
import com.example.cms_backend.Services.NotificationServices.*;
import jakarta.servlet.http.HttpServletRequest;
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
    private final GetParishNotificationsService getParishNotificationsService;

    public NotificationController(CreateNotificationService createNotificationService,
                                  DeleteNotificationService deleteNotificationService,
                                  UpdateNotificationService updateNotificationService,
                                  GetNotificationService getNotificationService,
                                  GetNotificationsService getNotificationsService,
                                  GetParishNotificationsService getParishNotificationsService) {
        this.createNotificationService = createNotificationService;
        this.deleteNotificationService = deleteNotificationService;
        this.updateNotificationService = updateNotificationService;
        this.getNotificationService = getNotificationService;
        this.getNotificationsService = getNotificationsService;
        this.getParishNotificationsService = getParishNotificationsService;
    }

    @PostMapping("/notification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification,
                                                           HttpServletRequest request) {
        CreateNotificationCommand command = new CreateNotificationCommand(notification, request);
        return createNotificationService.execute(command);
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable Long id) {
        return getNotificationService.execute(id);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(HttpServletRequest request) {
        return getNotificationsService.execute(request);
    }

    @PutMapping("/notification/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long id, @RequestBody Notification notification, HttpServletRequest request) {
        return updateNotificationService.execute(new UpdateNotificationCommand(id, notification, request));
    }

    @DeleteMapping("/notification/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        return deleteNotificationService.execute(id);
    }

    /*
    AUTHORIZED ENDPOINTS
     */

    @GetMapping("/notifications/parish")
    public ResponseEntity<List<Notification>> getParishNotifications(HttpServletRequest request) {
        return getParishNotificationsService.execute(request);
    }

}


