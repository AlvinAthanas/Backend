package com.example.cms_backend.Model.UpdateCommands;

import com.example.cms_backend.Model.Entities.Event;

public class UpdateEventCommand {
    private Long id;
    private Event event;

    public UpdateEventCommand(Long id, Event event) {
        this.id = id;
        this.event = event;
    }

    public Long getId() {
        return id;
    }

    public Event getEvent() {
        return event;
    }
}
