package com.example.cms_backend.services.EventServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.EventNotFoundException;
import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetEventService implements Query<Long, Event>{
    private final EventRepository eventRepository;

    public GetEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<Event> execute(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            return ResponseEntity.ok(eventOptional.get());
        }
        throw new EventNotFoundException();
    }
}
