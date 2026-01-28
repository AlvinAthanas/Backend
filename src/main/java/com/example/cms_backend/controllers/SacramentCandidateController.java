package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.CreateSacramentCandidateCommand;
import com.example.cms_backend.model.Commands.GetSessionFilterCommand;
import com.example.cms_backend.model.Commands.UpdateSacramentCandidateCommand;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.model.Enums.SacramentType;
import com.example.cms_backend.services.SacramentCandidateServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("hasRole('CATECHIST')")
public class SacramentCandidateController {
    private final CreateSacramentCandidateService createService;
    private final GetSacramentCandidateService getService;
    private final GetSacramentCandidatesService listService;
    private final UpdateSacramentCandidateService updateService;
    private final DeleteSacramentCandidateService deleteService;
    private final GetSacramentCandidatesBySessionService getCandidatesBySession;

    public SacramentCandidateController(CreateSacramentCandidateService createService,
                                        GetSacramentCandidateService getService,
                                        GetSacramentCandidatesService listService,
                                        UpdateSacramentCandidateService updateService,
                                        DeleteSacramentCandidateService deleteService,
                                        GetSacramentCandidatesBySessionService getCandidatesBySession) {
        this.createService = createService;
        this.getService = getService;
        this.listService = listService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.getCandidatesBySession = getCandidatesBySession;
    }

    @PreAuthorize("hasAuthority('WRITE_SACRAMENTS')")
    @PostMapping("/sacrament/candidate")
    public ResponseEntity<SacramentCandidate> create(@RequestBody CreateSacramentCandidateCommand command) {
        return createService.execute(command);
    }

    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/candidate/{id}")
    public ResponseEntity<SacramentCandidate> get(@PathVariable Long id) {
        return getService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/candidates")
    public ResponseEntity<List<SacramentCandidate>> list() {
        return listService.execute(null);
    }

    @PreAuthorize("hasAuthority('WRITE_SACRAMENTS')")
    @PutMapping("/candidate/{id}")
    public ResponseEntity<SacramentCandidate> update(@PathVariable Long id, @RequestBody UpdateSacramentCandidateCommand command) {
        command.setId(id);
        return updateService.execute(command);
    }

    @PreAuthorize("hasAuthority('WRITE_SACRAMENTS')")
    @DeleteMapping("/candidate/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return deleteService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/sacrament-candidates-for-session")
    public ResponseEntity<List<SacramentCandidate>> getCandidatesBySession(
            HttpServletRequest request,
            @RequestParam SacramentType type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        GetSessionFilterCommand command = new GetSessionFilterCommand(request, type, startDate, endDate);
        return getCandidatesBySession.execute(command);
    }
}