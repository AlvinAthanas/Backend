package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateEventService implements Command<Event,Event> {
    private final EventRepository eventRepository;

    public CreateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<Event> execute(Event event) {
        eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
