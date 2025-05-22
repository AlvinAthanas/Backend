package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.GetEventsQuery;
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

@Service
public class GetEventsService implements Query<GetEventsQuery, List<Event>> {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public GetEventsService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Event>> execute(GetEventsQuery input) {
        return execute(input, null);
    }

    public ResponseEntity<List<Event>> execute(GetEventsQuery input, HttpServletRequest request) {
        List<Event> events;

        // If parishId is explicitly provided in the query, use it
        if (input != null && input.getParishId() != null) {
            events = eventRepository.findByParishId(input.getParishId());
        } 
        // Otherwise, if request is provided, filter by logged-in user's parishId
        else if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        events = eventRepository.findByParishId(parishId);
                    } else {
                        events = eventRepository.findAll();
                    }
                } else {
                    events = eventRepository.findAll();
                }
            } else {
                events = eventRepository.findAll();
            }
        } 
        // If neither parishId nor request is provided, return all events
        else {
            events = eventRepository.findAll();
        }

        return ResponseEntity.ok(events);
    }
}
