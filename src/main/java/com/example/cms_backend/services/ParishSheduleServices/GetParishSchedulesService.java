package com.example.cms_backend.services.ParishSheduleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.ParishSchedules;
import com.example.cms_backend.model.Entities.Event;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.EventRepository;
import com.example.cms_backend.repositories.ParishRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetParishSchedulesService implements Query<HttpServletRequest, List<ParishSchedules>> {

    private final ParishRepository parishRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public GetParishSchedulesService(ParishRepository parishRepository,
                                     EventRepository eventRepository,
                                     UserRepository userRepository) {
        this.parishRepository = parishRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<ParishSchedules>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email == null) {
            return ResponseEntity.badRequest().build();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        List<ParishSchedules> parishSchedulesList = new ArrayList<>();

        // Include user's own parish
        Parish userParish = parishRepository.findById(user.getParishId())
                .orElseThrow(() -> new IllegalArgumentException("Parish not found with ID: " + user.getParishId()));
        List<Event> userParishMasses = eventRepository.findByParishIdAndDescription(userParish.getId(), "Mass");
        parishSchedulesList.add(new ParishSchedules(userParish, new ArrayList<>(userParishMasses)));

        // Include favorite parishes
        if (user.getFavoriteParishes() != null) {
            for (Parish favParish : user.getFavoriteParishes()) {
                List<Event> favParishMasses = eventRepository.findByParishIdAndDescription(favParish.getId(), "Mass");
                parishSchedulesList.add(new ParishSchedules(favParish, new ArrayList<>(favParishMasses)));
            }
        }

        return ResponseEntity.ok(parishSchedulesList);
    }

}
