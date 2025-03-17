package com.example.cms_backend.Services.EventServices;

import org.springframework.stereotype.Service;

@Service
public class DeleteEventService {
    private final DeleteEventService deleteEventService;

    public DeleteEventService(DeleteEventService deleteEventService) {
        this.deleteEventService = deleteEventService;
    }
}
