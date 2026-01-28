package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Notification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class CreateNotificationCommand {
    private final Notification notification;
    private final HttpServletRequest request;

    public CreateNotificationCommand(Notification notification, HttpServletRequest request) {
        this.notification = notification;
        this.request = request;
    }

}
