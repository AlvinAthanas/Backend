package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.EventRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateEventService implements Command<Event,Event> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public CreateEventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Event> execute(Event event) {
        return execute(event, null);
    }

    public ResponseEntity<Event> execute(Event event, HttpServletRequest request) {
        // Check if parishId is null and set it from logged-in user if available
        if (event.getParishId() == null && request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    event.setParishId(loggedInUser.getParishId());
                    // Also set the userId to the logged-in user's id
                    event.setUserId(loggedInUser.getId());
                }
            }
            // If no logged-in user or logged-in user has no parishId, leave parishId as null
        }

        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
