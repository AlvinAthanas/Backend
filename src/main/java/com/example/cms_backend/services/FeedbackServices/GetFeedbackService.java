package com.example.cms_backend.services.FeedbackServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.FeedbackNotFoundException;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.repositories.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetFeedbackService implements Query<Long, Feedback> {
    private final FeedbackRepository feedbackRepository;

    public GetFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<Feedback> execute(Long id) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(id);
        if (feedbackOptional.isPresent()) {
            return ResponseEntity.ok(feedbackOptional.get());
        }
        throw new FeedbackNotFoundException();
    }
}
