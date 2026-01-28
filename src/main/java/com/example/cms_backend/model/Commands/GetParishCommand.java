package com.example.cms_backend.model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class GetParishCommand {
    private Long id;
    private HttpServletRequest request;

    public GetParishCommand(Long id, HttpServletRequest request) {
        this.id = id;
        this.request = request;
    }
}
