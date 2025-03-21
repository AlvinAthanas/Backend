package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.FeedbackNotFoundException;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Repositories.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetFeedbacksService implements Query<Void, List<Feedback>> {
    private final FeedbackRepository feedbackRepository;

    public GetFeedbacksService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<List<Feedback>> execute(Void input) {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbacks);
    }
}
