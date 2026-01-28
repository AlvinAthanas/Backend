package com.example.cms_backend.services.FeedbackServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.CreateFeedbackCommand;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.FeedbackRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class CreateFeedbackService implements Command<CreateFeedbackCommand, Feedback> {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public CreateFeedbackService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Feedback> execute(CreateFeedbackCommand command) {
        Feedback feedback = command.getFeedback();
        String userEmail = LoggedInUserUtil.loggedInUserEmail(command.getRequest());

        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        feedback.setParishId(user.getParishId());
        feedback.setDate(LocalDate.now());

        feedbackRepository.save(feedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(feedback);
    }
}
