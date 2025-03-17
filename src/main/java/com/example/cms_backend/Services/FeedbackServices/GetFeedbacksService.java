package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Repositories.FeedbackRepository;
import org.springframework.stereotype.Service;

@Service
public class GetFeedbacksService {
    private final FeedbackRepository feedbackRepository;

    public GetFeedbacksService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
}
