package com.example.cms_backend.Services.EventServices;

import org.springframework.stereotype.Service;

@Service
public class GetEventsService {
    private final GetEventsService getEventsService;

    public GetEventsService(GetEventsService getEventsService) {
        this.getEventsService = getEventsService;
    }
}
