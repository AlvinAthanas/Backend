package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.CreateSacramentRegistrationCommand;
import com.example.cms_backend.Model.Commands.GetSessionFilterCommand;
import com.example.cms_backend.Model.Commands.UpdateSacramentRegistrationCommand;
import com.example.cms_backend.Model.DTO.SacramentSessionInfo;
import com.example.cms_backend.Model.Entities.SacramentRegistration;
import com.example.cms_backend.Model.Enums.SacramentType;
import com.example.cms_backend.Services.SacramentRegistrationServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SacramentRegistrationController {

    private final CreateSacramentRegistrationService createService;
    private final GetSacramentRegistrationService getService;
    private final GetSacramentRegistrationsService listService;
    private final UpdateSacramentRegistrationService updateService;
    private final DeleteSacramentRegistrationService deleteService;
    private final GetSacramentSessionsService getSacramentSessionsService;

    public SacramentRegistrationController(CreateSacramentRegistrationService createService,
                                           GetSacramentRegistrationService getService,
                                           GetSacramentRegistrationsService listService,
                                           UpdateSacramentRegistrationService updateService,
                                           DeleteSacramentRegistrationService deleteService,
                                           GetSacramentSessionsService getSacramentSessionsService) {
        this.createService = createService;
        this.getService = getService;
        this.listService = listService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.getSacramentSessionsService = getSacramentSessionsService;
    }

    @PostMapping("/sacrament")
    public ResponseEntity<SacramentRegistration> create(
            @RequestBody CreateSacramentRegistrationCommand command,
            HttpServletRequest request
    ) {
        command.setRequest(request);  // Set it before executing

        return createService.execute(command);
    }


    @GetMapping("/sacrament/{id}")
    public ResponseEntity<SacramentRegistration> get(@PathVariable Long id) {
        return getService.execute(id);
    }

    @GetMapping("/sacraments")
    public ResponseEntity<List<SacramentRegistration>> list() {
        return listService.execute(null);
    }

    @PutMapping("sacrament/{id}")
    public ResponseEntity<SacramentRegistration> update(@PathVariable Long id, @RequestBody UpdateSacramentRegistrationCommand command) {
        command.setId(id);
        return updateService.execute(command);
    }

    @DeleteMapping("/sacrament/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return deleteService.execute(id);
    }

    @GetMapping("/sacrament/sessions")
    public ResponseEntity<List<SacramentSessionInfo>> getSessionsByParish(
            HttpServletRequest request,
            @RequestParam SacramentType type
    ) {
        GetSessionFilterCommand command = new GetSessionFilterCommand(request, type, null, null);
        return getSacramentSessionsService.execute(command);
    }

}
