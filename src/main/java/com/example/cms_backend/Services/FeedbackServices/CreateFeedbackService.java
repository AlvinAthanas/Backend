package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Repositories.FeedbackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateFeedbackService implements Command<Feedback,Feedback> {
    private final FeedbackRepository feedbackRepository;

    public CreateFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<Feedback> execute(Feedback feedback) {
        feedbackRepository.save(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
}
