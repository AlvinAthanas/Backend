package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateEventService {
    private final EventRepository eventRepository;

    public CreateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
