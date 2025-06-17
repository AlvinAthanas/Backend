package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Notification;
import jakarta.servlet.http.HttpServletRequest;

public class CreateNotificationCommand {
    private final Notification notification;
    private final HttpServletRequest request;

    public CreateNotificationCommand(Notification notification, HttpServletRequest request) {
        this.notification = notification;
        this.request = request;
    }

    public Notification getNotification() {
        return notification;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
