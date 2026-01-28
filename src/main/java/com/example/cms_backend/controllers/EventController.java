package com.example.cms_backend.controllers;

import com.example.cms_backend.model.DTO.EventDTO;
import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.model.Commands.UpdateEventCommand;
import com.example.cms_backend.services.EventServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @PreAuthorize("hasAuthority('WRITE_EVENTS')")
    @PostMapping("/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event, HttpServletRequest request) {
        return createEventService.execute(event, request);
    }


    @GetMapping("/event/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return getEventService.execute(id);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents(HttpServletRequest request) {
        return getEventsService.execute(request);
    }



    @PreAuthorize("hasAuthority('WRITE_EVENTS')")
    @PutMapping("/event/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return updateEventService.execute(new UpdateEventCommand(id, event));
    }

    @PreAuthorize("hasAuthority('WRITE_EVENTS')")
    @DeleteMapping("/event/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        return deleteEventService.execute(id);
    }

    @PreAuthorize("hasAuthority('WRITE_EVENTS')")
    @PostMapping("/event/schedule")
    public ResponseEntity<Event> createMassEvent(@RequestBody Event event, HttpServletRequest request) {
        event.setDescription("Mass");
        event.setLocation("Church");

        // Example: Set a dummy date with only the time relevant
        LocalTime timeOnly = event.getDateTime().toLocalTime();
        LocalDateTime dummyDateTime = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(timeOnly); // set to this week's Sunday
        event.setDateTime(dummyDateTime);


        System.out.println("Saved Event: " + event);
        return createEventService.execute(event, request);
    }


    @GetMapping("/event/schedules")
    public ResponseEntity<List<Event>> getMassEvents(@RequestParam String description, HttpServletRequest request) {
        return getMassSchedulesService.execute(description, request);
    }
}
