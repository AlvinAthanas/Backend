package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.CreateFeedbackCommand;
import com.example.cms_backend.model.Entities.Feedback;
import com.example.cms_backend.model.Commands.UpdateFeedbackCommand;
import com.example.cms_backend.services.FeedbackServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {
    private final CreateFeedbackService createFeedbackService;
    private final DeleteFeedbackService deleteFeedbackService;
    private final UpdateFeedbackService updateFeedbackService;
    private final GetFeedbackService getFeedbackService;
    private final GetFeedbacksService getFeedbacksService;

    public FeedbackController(CreateFeedbackService createFeedbackService,
                              DeleteFeedbackService deleteFeedbackService,
                              UpdateFeedbackService updateFeedbackService,
                              GetFeedbackService getFeedbackService,
                              GetFeedbacksService getFeedbacksService) {
        this.createFeedbackService = createFeedbackService;
        this.deleteFeedbackService = deleteFeedbackService;
        this.updateFeedbackService = updateFeedbackService;
        this.getFeedbackService = getFeedbackService;
        this.getFeedbacksService = getFeedbacksService;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback, HttpServletRequest request) {
        return createFeedbackService.execute(new CreateFeedbackCommand(feedback, request));
    }


    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        return getFeedbackService.execute(id);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbacks(HttpServletRequest request) {
        return getFeedbacksService.execute(request);
    }


    @PutMapping("/feedback/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable Long id, @RequestBody Feedback feedback) {
        return updateFeedbackService.execute(new UpdateFeedbackCommand(id, feedback));
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        return deleteFeedbackService.execute(id);
    }
}
