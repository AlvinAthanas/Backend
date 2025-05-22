package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.EventRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
