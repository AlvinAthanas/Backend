package com.example.cms_backend.Services.EventServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.EventNotFoundException;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Repositories.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteEventService implements Command<Long,Void> {
    private final EventRepository eventRepository;

    public DeleteEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new EventNotFoundException();
    }
}
