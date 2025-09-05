package com.ectd.global.eln.services;

import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.dto.AuditHeaderDto;
import com.ectd.global.eln.utils.AuditReportHeader;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

@Service
public class AuditReportGenerateServiceImpl implements AuditReportGenerateService {

	public byte[] generateAuditReportFromView(List<AuditLogDto> auditLogs,
            String fromDate,
            String toDate,
            String userName) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//          Document document = new Document(PageSize.A4.rotate(), 36, 36, 90, 36);
        	Document document = new Document(PageSize.A3.rotate(), 36, 36, 90, 36);

        	AuditHeaderDto headerDto = new AuditHeaderDto(fromDate, toDate, userName);
        	PdfWriter writer = PdfWriter.getInstance(document, baos);
        	writer.setPageEvent(new AuditReportHeader(headerDto));

            document.open();

            // Table with 7 columns
            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
//            table.setWidths(new float[]{1, 2, 2, 2, 4, 2, 2});
            table.setWidths(new float[]{1, 2, 2, 3, 6, 3, 3});


            // Add table header
            addTableHeader(table);

            // Repeat the first row (header) on every page
            table.setHeaderRows(1);

            // Font for table content
//            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 7);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);  // or 11


            // Table data
            int serial = 1;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            for (AuditLogDto log : auditLogs) {
                addCell(table, String.valueOf(serial++), cellFont);            // S.No
                addCell(table, log.getUserName(), cellFont);                   // User Name
                addCell(table, log.getEventType(), cellFont);                  // Event
                addCell(table, log.getModuleSection(), cellFont);              // Module / Section
                addCell(table, log.getAction(), cellFont);                     // Action Performed
                addCell(table, sdf.format(log.getCreatedDate()), cellFont);    // Date/Time
                addCell(table, log.getIpAddress(), cellFont);                  // IP Address
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating audit PDF", e);
        }
    }


    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8, BaseColor.WHITE);
        BaseColor bootstrapBlue = new BaseColor(13, 110, 253); // #0d6efd

        Stream.of("S.No", "User Name", "Event","Module / Section", "Action Performed",
       		 "Date/Time", "IP Address")
              .forEach(headerTitle -> {
                  PdfPCell header = new PdfPCell(new Phrase(headerTitle, headerFont));
                  header.setBackgroundColor(bootstrapBlue);
                  header.setHorizontalAlignment(Element.ALIGN_CENTER);
                  header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                  header.setPadding(8);
                  table.addCell(header);
              });
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "", font));
        cell.setNoWrap(false); // allow wrapping at spaces
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        table.addCell(cell);
    }
}
