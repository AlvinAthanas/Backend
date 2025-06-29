package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.CreateGroupContributionRequirementCommand;
import com.example.cms_backend.Model.Commands.SubmitGroupContributionCommand;
import com.example.cms_backend.Model.DTO.GroupContributionDebtDTO;
import com.example.cms_backend.Model.DTO.GroupContributionRequirementDTO;
import com.example.cms_backend.Model.DTO.GroupContributionSubmissionDTO;
import com.example.cms_backend.Model.DTO.GroupContributionSubmissionViewDTO;
import com.example.cms_backend.Services.GroupContributionServices.*;
import com.example.cms_backend.Utils.GroupContributionExcelExporter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/group/contributions")
@RequiredArgsConstructor
public class GroupContributionController {

    private final CreateGroupContributionRequirementService createRequirementService;
    private final GetGroupContributionDeclarationsService getGroupContributionDeclarationsService;
    private final SubmitGroupContributionService submitGroupContributionService;
    private final GetGroupContributionSubmissionsService getGroupContributionSubmissionsService;
    private final ExportGroupContributionPDFService exportGroupContributionPDFService;
    private final GetMyGroupContributionSubmissionsService getMyGroupContributionSubmissionsService;
    private final GetUserGroupContributionDebtsService getUserGroupContributionDebtsService;

    @PreAuthorize("hasRole('PARISHIONER') or hasRole('COMMITTEE_CHAIRPERSON') or hasRole('COMMITTEE_SECRETARY') " +
            "or hasRole('COMMITTEE_TREASURER') or hasRole('COMMUNITY_CHAIRPERSON') or hasRole('COMMUNITY_SECRETARY')" +
            "or hasRole('COMMUNITY_TREASURER') or hasAuthority('WRITE_COMMUNITIES') or hasAuthority('WRITE_CONTRIBUTIONS')")
    @PostMapping("/declare")
    public ResponseEntity<GroupContributionRequirementDTO> declareContribution(
            @RequestBody CreateGroupContributionRequirementCommand command,
            HttpServletRequest request
    ) {
        command.setRequest(request);  // Manually inject request into command
        return createRequirementService.execute(command);
    }


    @GetMapping("/declarations")
    public ResponseEntity<List<GroupContributionRequirementDTO>> getGroupContributionDeclarations(
            HttpServletRequest request,
            @RequestParam(name = "fulfilled", required = false) Boolean fulfilled
    ) {
        return getGroupContributionDeclarationsService.execute(request, fulfilled);
    }

//    @PreAuthorize("hasRole('COMMUNITY_CHAIRPERSON') or hasRole('COMMUNITY_SECRETARY') or hasRole('COMMUNITY_TREASURER')")
    @PostMapping("/submit")
    public ResponseEntity<GroupContributionSubmissionDTO> submitContribution(
            @RequestBody SubmitGroupContributionCommand command
    ) {
        return submitGroupContributionService.execute(command);
    }

    @GetMapping("/requirement/{requirementId}/submissions")
    public ResponseEntity<List<GroupContributionSubmissionViewDTO>> getSubmissionsByRequirement(
            @PathVariable Long requirementId
    ) {
        return getGroupContributionSubmissionsService.execute(requirementId);
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/declarations/export")
    public ResponseEntity<byte[]> exportGroupDeclarationsToExcel(
            HttpServletRequest request,
            @RequestParam(name = "fulfilled", required = false) Boolean fulfilled
    ) throws IOException {
        List<GroupContributionRequirementDTO> data = getGroupContributionDeclarationsService.execute(request, fulfilled).getBody();

        ByteArrayInputStream excelStream = GroupContributionExcelExporter.export(data);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=group_contributions.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(excelStream.readAllBytes());
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/declarations/export/pdf")
    public ResponseEntity<byte[]> exportDeclarationsPdf(
            HttpServletRequest request,
            @RequestParam(required = false) Boolean fulfilled
    ) {
        return exportGroupContributionPDFService.export(request, fulfilled);
    }


    @GetMapping("/my-submissions")
    public ResponseEntity<List<GroupContributionSubmissionDTO>> getMySubmissions(HttpServletRequest request) {
        return getMyGroupContributionSubmissionsService.execute(request);
    }

    @GetMapping("/my-debts")
    public ResponseEntity<List<GroupContributionDebtDTO>> getUserDebts(HttpServletRequest request) {
        return getUserGroupContributionDebtsService.execute(request);
    }


}