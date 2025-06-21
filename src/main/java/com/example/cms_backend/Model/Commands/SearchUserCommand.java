package com.example.cms_backend.Model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class SearchUserCommand {
    private final String name;
    private final HttpServletRequest request;

    public SearchUserCommand(String name, HttpServletRequest request) {
        this.name = name;
        this.request = request;
    }
}
