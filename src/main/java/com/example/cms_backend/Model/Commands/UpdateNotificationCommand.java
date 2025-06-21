package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Notification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class UpdateNotificationCommand {
    private Long id;
    private Notification notification;
    private HttpServletRequest request;

    public UpdateNotificationCommand(Long id,
                                     Notification notification,
                                     HttpServletRequest request) {
        this.id = id;
        this.notification = notification;
        this.request = request;
    }

}
