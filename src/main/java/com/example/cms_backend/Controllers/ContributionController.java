package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.DTO.ContributionFilterDTO;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.UpdateCommands.UpdateContributionCommand;
import com.example.cms_backend.Services.ContributionServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping("/contribution")
    public ResponseEntity<Contribution> addContribution(@RequestBody Contribution contribution) {
        return createContributionService.execute(contribution);
    }

    @GetMapping("/contribution/{id}")
    public ResponseEntity<Contribution> getContribution(@PathVariable Long id) {
        return getContributionService.execute(id);
    }

    @GetMapping("/contributions")
    public ResponseEntity<List<Contribution>> getContributions() {
        return getContributionsService.execute(null);
    }

    @PutMapping("/contribution/{id}")
    public ResponseEntity<Contribution> updateContribution(@PathVariable Long id, @RequestBody Contribution contribution) {
        return updateContributionService.execute(new UpdateContributionCommand(id,contribution));
    }

    @DeleteMapping("/contribution/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable Long id) {
        return deleteContributionService.execute(id);
    }

    @GetMapping("/contributions/filter")
    public ResponseEntity<List<Contribution>> getFilteredContributions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        ContributionFilterDTO filterDTO = new ContributionFilterDTO(type, month, year);
        return filteredContributionsService.execute(filterDTO);
    }

    @GetMapping("/contributions/totalAmount")
    public ResponseEntity<Long> getTotalAmount() {
        return getTotalAmountService.execute(null);
    }
}
