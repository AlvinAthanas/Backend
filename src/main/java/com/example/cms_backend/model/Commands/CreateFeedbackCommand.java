package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Feedback;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFeedbackCommand {
    private Feedback feedback;
    private HttpServletRequest request;
}
