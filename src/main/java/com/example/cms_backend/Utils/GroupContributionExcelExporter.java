package com.example.cms_backend.Utils;

import com.example.cms_backend.Model.DTO.GroupContributionRequirementDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class GroupContributionExcelExporter {

    public static ByteArrayInputStream export(List<GroupContributionRequirementDTO> declarations) throws IOException {
        String[] headers = {
                "Group Name", "Contribution Type", "Target Amount",
                "Collected Amount", "Deadline", "Status", "Description"
        };

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Group Declarations");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            // Header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Data rows
            int rowIdx = 1;
            for (GroupContributionRequirementDTO dto : declarations) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getGroupName());
                row.createCell(1).setCellValue(dto.getContributionType());
                row.createCell(2).setCellValue(dto.getTargetAmount());
                row.createCell(3).setCellValue(dto.getTotalContributedAmount());
                row.createCell(4).setCellValue(dto.getDeadline().toString());
                row.createCell(5).setCellValue(dto.isFulfilled() ? "Fulfilled" : "Pending");
                row.createCell(6).setCellValue(dto.getDescription());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
