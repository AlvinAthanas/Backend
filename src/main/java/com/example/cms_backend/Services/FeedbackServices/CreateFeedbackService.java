package com.example.cms_backend.Services.FeedbackServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.CreateFeedbackCommand;
import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.FeedbackRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
