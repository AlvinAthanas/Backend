package com.example.cms_backend.Services.GroupContributionServices;

import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupContributionRequirementRepository;
import com.example.cms_backend.Repositories.GroupContributionSubmissionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExportGroupContributionPDFService {

    private final UserRepository userRepository;
    private final GroupContributionRequirementRepository requirementRepository;
    private final GroupContributionSubmissionRepository submissionRepository;

    public ResponseEntity<byte[]> export(HttpServletRequest request, Boolean fulfilledFilter) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Set<Group> userGroups = user.getGroups();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font tableHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 11);

            document.add(new Paragraph("Group Contribution Declarations", titleFont));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 2, 2, 2, 2, 1.5f});

            String[] headers = {"Group", "Contribution Type", "Target Amount", "Total Submitted", "Deadline", "Fulfilled?"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, tableHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            userGroups.forEach(group ->
                    requirementRepository.findByGroupId(group.getId())
                            .forEach(requirement -> {
                                Long total = submissionRepository.sumByRequirementId(requirement.getId()).orElse(0L);
                                boolean fulfilled = total >= requirement.getTargetAmount();

                                if (fulfilledFilter == null || fulfilled == fulfilledFilter) {
                                    table.addCell(new Phrase(group.getName(), cellFont));
                                    table.addCell(new Phrase(requirement.getContributionType(), cellFont));
                                    table.addCell(new Phrase(String.valueOf(requirement.getTargetAmount()), cellFont));
                                    table.addCell(new Phrase(String.valueOf(total), cellFont));
                                    table.addCell(new Phrase(requirement.getDeadline().toString(), cellFont));
                                    table.addCell(new Phrase(fulfilled ? "Yes" : "No", cellFont));
                                }
                            })
            );

            document.add(table);
            document.close();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attachment; filename=group_contributions.pdf");

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
