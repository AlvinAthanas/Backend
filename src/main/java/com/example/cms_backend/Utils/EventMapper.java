package com.example.cms_backend.Utils;

import com.example.cms_backend.Model.DTO.EventDTO;
import com.example.cms_backend.Model.Entities.Event;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class EventMapper {

    /**
     * Maps a list of Event entities to a list of EventDTOs,
     * enriching with organizer name and phone number.
     */
    public static List<EventDTO> toDTOList(List<Event> events, UserRepository userRepository) {
        return events.stream().map(event -> mapToDTO(event, userRepository)).toList();
    }

    /**
     * Maps a single Event entity to an EventDTO.
     */
    public static EventDTO mapToDTO(Event event, UserRepository userRepository) {
        Optional<User> organizerOpt = Optional.empty();

        if (event.getUserId() != null) {
            organizerOpt = userRepository.findById(event.getUserId());
        }

        String organizerName = organizerOpt.map(User::getName).orElse(null);
        String organizerPhone = organizerOpt.map(User::getPhone).orElse(null);

        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getLocation(),
                event.getDateTime(),
                event.getParishId(),
                event.getUserId(),
                organizerName,
                organizerPhone
        );
    }

}

