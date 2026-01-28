package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.CreateSacramentRegistrationCommand;
import com.example.cms_backend.model.Commands.GetSessionFilterCommand;
import com.example.cms_backend.model.Commands.UpdateSacramentCompletionStatusCommand;
import com.example.cms_backend.model.Commands.UpdateSacramentRegistrationCommand;
import com.example.cms_backend.model.DTO.SacramentSessionInfo;
import com.example.cms_backend.model.Entities.SacramentRegistration;
import com.example.cms_backend.model.Enums.SacramentType;
import com.example.cms_backend.services.SacramentRegistrationServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('CATECHIST') or hasAuthority('WRITE_SACRAMENTS')")
public class SacramentRegistrationController {

    private final CreateSacramentRegistrationService createService;
    private final GetSacramentRegistrationService getService;
    private final GetSacramentRegistrationsService listService;
    private final UpdateSacramentRegistrationService updateService;
    private final DeleteSacramentRegistrationService deleteService;
    private final GetSacramentSessionsService getSacramentSessionsService;

    private final UpdateSacramentCompletionStatusService updateCompletionStatusService;

    public SacramentRegistrationController(CreateSacramentRegistrationService createService,
                                           GetSacramentRegistrationService getService,
                                           GetSacramentRegistrationsService listService,
                                           UpdateSacramentRegistrationService updateService,
                                           DeleteSacramentRegistrationService deleteService,
                                           GetSacramentSessionsService getSacramentSessionsService,
                                           UpdateSacramentCompletionStatusService updateCompletionStatusService) {
        this.createService = createService;
        this.getService = getService;
        this.listService = listService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.getSacramentSessionsService = getSacramentSessionsService;
        this.updateCompletionStatusService = updateCompletionStatusService;
    }

    @PutMapping("/sacrament/completion")
    public ResponseEntity<SacramentRegistration> updateCompletionStatus(
            @RequestBody UpdateSacramentCompletionStatusCommand command) {
        return updateCompletionStatusService.execute(command);
    }


    @PostMapping("/sacrament")
    public ResponseEntity<SacramentRegistration> create(
            @RequestBody CreateSacramentRegistrationCommand command,
            HttpServletRequest request
    ) {
        command.setRequest(request);  // Set it before executing

        return createService.execute(command);
    }


    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/sacrament/{id}")
    public ResponseEntity<SacramentRegistration> get(@PathVariable Long id) {
        return getService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/sacraments")
    public ResponseEntity<List<SacramentRegistration>> list() {
        return listService.execute(null);
    }

    @PutMapping("/sacrament/{id}")
    public ResponseEntity<SacramentRegistration> update(@PathVariable Long id, @RequestBody UpdateSacramentRegistrationCommand command) {
        command.setId(id);
        return updateService.execute(command);
    }

    @DeleteMapping("/sacrament/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return deleteService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_SACRAMENTS')")
    @GetMapping("/sacrament/sessions")
    public ResponseEntity<List<SacramentSessionInfo>> getSessionsByParish(
            HttpServletRequest request,
            @RequestParam SacramentType type
    ) {
        GetSessionFilterCommand command = new GetSessionFilterCommand(request, type, null, null);
        return getSacramentSessionsService.execute(command);
    }

}
