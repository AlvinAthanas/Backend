package com.example.cms_backend.services.FeedbackServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.FeedbackRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFeedbacksService implements Query<HttpServletRequest, List<Feedback>> {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public GetFeedbacksService(FeedbackRepository feedbackRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Feedback>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Long parishId = user.getParishId();  // Assuming your User entity has a `parishId` field

        List<Feedback> feedbacks = feedbackRepository.findByParishIdAndReceiverIdIsNullOrReceiverId(parishId, user.getId());

        return ResponseEntity.ok(feedbacks);
    }

}
