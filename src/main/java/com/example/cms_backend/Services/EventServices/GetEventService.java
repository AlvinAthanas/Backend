package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class GetEventService {
    private final EventRepository eventRepository;

    public GetEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
