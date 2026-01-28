package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.ParishSchedules;
import com.example.cms_backend.services.ParishSheduleServices.GetParishSchedulesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class ParishSchedulesController {
    private final GetParishSchedulesService getParishSchedulesService;

    public ParishSchedulesController(GetParishSchedulesService getParishSchedulesService) {
        this.getParishSchedulesService = getParishSchedulesService;
    }

    @GetMapping("/parish/mass-schedules")
    public ResponseEntity<List<ParishSchedules>> getMassSchedulesForLoggedUser(HttpServletRequest request) {
        return getParishSchedulesService.execute(request);
    }

}
