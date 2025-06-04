package com.ectd.global.eln.services;

import com.ectd.global.eln.audit.AuditLogDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AuditReportServiceImpl implements AuditReportService {

    public byte[] generateAuditReportFromView(List<AuditLogDto> auditLogs) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("Audit Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15f);
            document.add(title);

            // Table with 5 columns
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 2, 3, 5, 4}); // Adjust widths as needed

            addTableHeader(table);

            int serial = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, hh:mm a");

            for (AuditLogDto log : auditLogs) {
                table.addCell(String.valueOf(serial++));
                table.addCell(String.valueOf(log.getId()));
                table.addCell(log.getUserName());
                table.addCell(log.getAction());
                table.addCell(sdf.format(log.getCreatedDate()));
            }

            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating audit PDF", e);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("S.No", "ID", "User", "Action", "Date").forEach(headerTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setPhrase(new Phrase(headerTitle));
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPadding(5);
            table.addCell(header);
        });
    }
}
