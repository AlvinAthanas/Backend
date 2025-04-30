package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Feedback;
import com.example.cms_backend.Model.Commands.UpdateFeedbackCommand;
import com.example.cms_backend.Services.FeedbackServices.*;
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
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        return createFeedbackService.execute(feedback);
    }

    @GetMapping("/feedback/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        return getFeedbackService.execute(id);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<Feedback>> getFeedbacks() {
        return getFeedbacksService.execute(null);
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
