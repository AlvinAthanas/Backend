package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.User;

public class UpdateUserCommand {
    private Long id;
    private User user;

    public UpdateUserCommand(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
