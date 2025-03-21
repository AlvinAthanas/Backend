package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.FeedbackNotFoundException;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Repositories.FeedbackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteFeedbackService implements Command<Long,Void> {
    private final FeedbackRepository feedbackRepository;

    public DeleteFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            feedbackRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new FeedbackNotFoundException();
    }
}
