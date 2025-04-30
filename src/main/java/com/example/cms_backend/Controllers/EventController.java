package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Commands.UpdateEventCommand;
import com.example.cms_backend.Services.EventServices.*;
import org.springframework.http.ResponseEntity;
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

        // Example: Set a dummy date with only the time relevant
        LocalTime timeOnly = event.getDateTime().toLocalTime();
        LocalDateTime dummyDateTime = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(timeOnly); // set to this week's Sunday
        event.setDateTime(dummyDateTime);


        System.out.println("Saved Event: " + event);
        return createEventService.execute(event);
    }


    @GetMapping("/event/schedules")
    public ResponseEntity<List<Event>> getMassEvents(@RequestParam String description) {
        return getMassSchedulesService.execute(description);
    }
}
