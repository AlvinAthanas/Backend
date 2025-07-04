package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.DTO.ContributionFilterDTO;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.Commands.UpdateContributionCommand;
import com.example.cms_backend.Services.ContributionServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('COMMITTEE_TREASURER') or hasRole('PARISHIONER') or hasRole('COMMITTEE_SECRETARY') or hasRole('COMMITTEE_CHAIRPERSON')")
public class ContributionController {
    private final CreateContributionService createContributionService;
    private final UpdateContributionService updateContributionService;
    private final DeleteContributionService deleteContributionService;
    private final GetContributionService getContributionService;
    private final GetContributionsService getContributionsService;
    private final GetFilteredContributionsService filteredContributionsService;
    private final GetTotalAmountService getTotalAmountService;

    public ContributionController(CreateContributionService createContributionService,
                                  UpdateContributionService updateContributionService,
                                  DeleteContributionService deleteContributionService,
                                  GetContributionService getContributionService,
                                  GetContributionsService getContributionsService,
                                  GetFilteredContributionsService filteredContributionsService,
                                  GetTotalAmountService getTotalAmountService) {
        this.createContributionService = createContributionService;
        this.updateContributionService = updateContributionService;
        this.deleteContributionService = deleteContributionService;
        this.getContributionService = getContributionService;
        this.getContributionsService = getContributionsService;
        this.filteredContributionsService = filteredContributionsService;
        this.getTotalAmountService = getTotalAmountService;
    }

    @PreAuthorize("hasAuthority('WRITE_CONTRIBUTIONS')")
    @PostMapping("/contribution")
    public ResponseEntity<Contribution> addContribution(@RequestBody Contribution contribution, HttpServletRequest request) {
        return createContributionService.execute(contribution, request);
    }


    @GetMapping("/contribution/{id}")
    public ResponseEntity<Contribution> getContribution(@PathVariable Long id) {
        return getContributionService.execute(id);
    }
    @PreAuthorize("hasAuthority('READ_CONTRIBUTIONS')")
    @GetMapping("/contributions")
    public ResponseEntity<List<Contribution>> getContributions(HttpServletRequest request) {
        return getContributionsService.execute(null, request);
    }

    @PreAuthorize("hasAuthority('WRITE_CONTRIBUTIONS')")
    @PutMapping("/contribution/{id}")
    public ResponseEntity<Contribution> updateContribution(@PathVariable Long id, @RequestBody Contribution contribution, HttpServletRequest request) {
        return updateContributionService.execute(new UpdateContributionCommand(id,contribution), request);
    }

    @PreAuthorize("hasAuthority('WRITE_CONTRIBUTIONS')")
    @DeleteMapping("/contribution/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        return deleteContributionService.execute(id);
    }
    
    @PreAuthorize("hasAuthority('READ_CONTRIBUTIONS')")
    @GetMapping("/contributions/filter")
    public ResponseEntity<List<Contribution>> getFilteredContributions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            HttpServletRequest request) {
        ContributionFilterDTO filterDTO = new ContributionFilterDTO(type, month, year);
        return filteredContributionsService.execute(filterDTO, request);
    }

    @GetMapping("/contributions/totalAmount")
    public ResponseEntity<Long> getTotalAmount(HttpServletRequest request) {
        return getTotalAmountService.execute(null, request);
    }
}
