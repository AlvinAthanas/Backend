package com.example.cms_backend.services.EventServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.DTO.EventDTO;
import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.EventRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.EventMapper;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GetEventsService implements Query<Void, List<EventDTO>> {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public GetEventsService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<EventDTO>> execute(Void input) {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOs = EventMapper.toDTOList(events, userRepository);
        return ResponseEntity.ok(eventDTOs);
    }

    public ResponseEntity<List<EventDTO>> execute(HttpServletRequest request) {
        Long parishId = getParishIdFromRequest(request);
        List<Event> events = (parishId != null)
                ? eventRepository.findByParishId(parishId)
                : eventRepository.findAll();

        List<EventDTO> eventDTOs = EventMapper.toDTOList(events, userRepository);
        return ResponseEntity.ok(eventDTOs);
    }

    private Long getParishIdFromRequest(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email == null) return null;

        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(User::getParishId).orElse(null);
    }
}

