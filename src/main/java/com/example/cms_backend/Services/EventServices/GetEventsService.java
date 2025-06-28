package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.EventDTO;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.EventRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.EventMapper;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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

