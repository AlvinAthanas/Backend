package com.example.cms_backend.Model.UpdateCommands;

import com.example.cms_backend.Model.Entities.Notification;

public class UpdateNotificationCommand {
    private Long id;
    private Notification notification;

    public UpdateNotificationCommand(Long id, Notification notification) {
        this.id = id;
        this.notification = notification;
    }

    public Long getId() {
        return id;
    }

    public Notification getNotification() {
        return notification;
    }
}
