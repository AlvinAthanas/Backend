package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.FeedbackNotFoundException;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Repositories.FeedbackRepository;
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
