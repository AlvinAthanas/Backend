package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.UpdateCommands.UpdateEventCommand;
import com.example.cms_backend.Services.EventServices.*;
import com.example.cms_backend.Services.FeedbackServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {


    private final CreateEventService createEventService;
    private final GetEventsService getEventsService;
    private final DeleteEventService deleteEventService;
    private final UpdateEventService updateEventService;
    private final GetEventService getEventService;
    private final GetMassSchedulesService getMassSchedulesService;

    public EventController(CreateEventService createEventService,
                           GetEventsService getEventsService,
                           DeleteEventService deleteEventService,
                           UpdateEventService updateEventService,
                           GetEventService getEventService,
                           GetMassSchedulesService getMassSchedulesService) {
        this.createEventService = createEventService;
        this.getEventsService = getEventsService;
        this.deleteEventService = deleteEventService;
        this.updateEventService = updateEventService;
        this.getEventService = getEventService;
        this.getMassSchedulesService = getMassSchedulesService;
    }



    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return createEventService.execute(event);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return getEventService.execute(id);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getEvents() {
        return getEventsService.execute(null);
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return updateEventService.execute(new UpdateEventCommand(id, event));
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return deleteEventService.execute(id);
    }

    @PostMapping("/event/schedule")
    public ResponseEntity<Event> createMassEvent(@RequestBody Event event) {
        event.setDescription("Mass");
        event.setLocation("Church");
        return createEventService.execute(event);
    }

    @GetMapping("/event/schedules")
    public ResponseEntity<List<Event>> getMassEvents(@RequestParam String description) {
        return getMassSchedulesService.execute(description);
    }
}
