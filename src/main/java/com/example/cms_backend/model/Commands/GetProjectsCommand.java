package com.example.cms_backend.model.Commands;

import jakarta.servlet.http.HttpServletRequest;

public class GetProjectsCommand {
    private final HttpServletRequest request;

    public GetProjectsCommand(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
