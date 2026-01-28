package com.example.cms_backend.services.FeedbackServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.FeedbackNotFoundException;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.model.Commands.UpdateFeedbackCommand;
import com.example.cms_backend.repositories.FeedbackRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateFeedbackService implements Command<UpdateFeedbackCommand, Feedback> {
    private final FeedbackRepository feedbackRepository;

    public UpdateFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<Feedback> execute(UpdateFeedbackCommand command) {
        Optional<Feedback> feedbackOptional = feedbackRepository.findById(command.getId());
        if (feedbackOptional.isPresent()) {
            Feedback feedback = command.getFeedback();
            feedback.setId(command.getId());
            feedbackRepository.save(feedback);
            return ResponseEntity.ok(feedback);
        }
        throw new FeedbackNotFoundException();
    }
}
