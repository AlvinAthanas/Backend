package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetMassSchedulesService implements Query<String, List<Event>> {
    private final EventRepository eventRepository;

    public GetMassSchedulesService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<List<Event>> execute(String description) {
        List<Event> schedules = eventRepository.findByDescriptionContaining(description);
        return ResponseEntity.ok(schedules);
    }
}
