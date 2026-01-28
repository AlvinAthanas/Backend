package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Feedback;

public class UpdateFeedbackCommand {
    private Long id;
    private Feedback feedback;

    public UpdateFeedbackCommand(Long id, Feedback feedback) {
        this.id = id;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public Feedback getFeedback() {
        return feedback;
    }
}
