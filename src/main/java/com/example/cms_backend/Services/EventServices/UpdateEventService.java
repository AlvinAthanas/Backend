package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.EventNotFoundException;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Commands.UpdateEventCommand;
import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateEventService implements Command<UpdateEventCommand, Event> {
    private final EventRepository eventRepository;

    public UpdateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<Event> execute(UpdateEventCommand command) {
        Optional<Event> eventOptional = eventRepository.findById(command.getId());
        if (eventOptional.isPresent()) {
            Event existingEvent = eventOptional.get();
            Event updatedEvent = command.getEvent();

            // Update fields selectively to preserve data not included in the update
            existingEvent.setName(updatedEvent.getName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setLocation(updatedEvent.getLocation());
            existingEvent.setDateTime(updatedEvent.getDateTime());

            // Don't update parishId and userId to preserve ownership
            // existingEvent.setParishId(updatedEvent.getParishId());
            // existingEvent.setUserId(updatedEvent.getUserId());

            eventRepository.save(existingEvent);
            return ResponseEntity.ok().body(existingEvent);
        }
        throw new EventNotFoundException();
    }
}
