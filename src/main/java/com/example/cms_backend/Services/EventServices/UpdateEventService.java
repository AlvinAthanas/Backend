package com.example.cms_backend.Services.EventServices;

import org.springframework.stereotype.Service;

@Service
public class UpdateEventService {
    private final UpdateEventService updateEventService;

    public UpdateEventService(UpdateEventService updateEventService) {
        this.updateEventService = updateEventService;
    }
}
