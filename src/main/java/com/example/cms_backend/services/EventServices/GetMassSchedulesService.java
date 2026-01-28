package com.example.cms_backend.services.EventServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.EventRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetMassSchedulesService implements Query<String, List<Event>> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public GetMassSchedulesService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Event>> execute(String description) {
        return execute(description, null);
    }

    public ResponseEntity<List<Event>> execute(String description, HttpServletRequest request) {
        List<Event> schedules = eventRepository.findByDescriptionContaining(description);

        // Filter events by parishId if request is provided
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        // Filter events by parishId
                        schedules = schedules.stream()
                                .filter(event -> parishId.equals(event.getParishId()))
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        return ResponseEntity.ok(schedules);
    }
}
