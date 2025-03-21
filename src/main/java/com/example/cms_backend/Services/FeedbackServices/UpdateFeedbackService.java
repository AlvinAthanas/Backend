package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.FeedbackNotFoundException;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Model.UpdateCommands.UpdateFeedbackCommand;
import com.example.cms_backend.Repositories.FeedbackRepository;
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
