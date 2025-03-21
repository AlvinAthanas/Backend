package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetEventsService implements Query<Void, List<Event>> {

    private final EventRepository eventRepository;

    public GetEventsService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<List<Event>> execute(Void input) {
        List<Event> events = eventRepository.findAll();
        return ResponseEntity.ok().body(events);
    }
}
