package com.example.cms_backend.services.FeedbackServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.FeedbackNotFoundException;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.repositories.FeedbackRepository;
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
