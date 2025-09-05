package com.ectd.global.eln.utils;

import com.ectd.global.eln.dto.AuditHeaderDto;
import com.itextpdf.text.Document;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AuditReportHeader extends PdfPageEventHelper {

    private Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);

    private Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 13);

    private Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

    private PdfTemplate total;
    private  AuditHeaderDto auditHeaderDto;
 
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }
    
    public AuditReportHeader(AuditHeaderDto auditHeaderDto) {
        this.auditHeaderDto = auditHeaderDto;
    }
    
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        Rectangle pageSize = document.getPageSize();

        // === HEADER ===
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setTotalWidth(pageSize.getWidth() - document.leftMargin() - document.rightMargin());
        headerTable.setLockedWidth(true);

        // Title row
        PdfPCell titleCell = new PdfPCell(new Phrase("NextGen_eLN Software Audit Report", headerFont));
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setPadding(10f);
        titleCell.setBorder(Rectangle.BOX);
        headerTable.addCell(titleCell);


     // Meta row (using DTO values)
        String metaText = String.format(
                "From Date: %s    To Date: %s    User: %s    |    Generated On: %s",
                safe(auditHeaderDto.getFromDate()),
                safe(auditHeaderDto.getToDate()),
                safe(auditHeaderDto.getUserName()),
                safe(auditHeaderDto.getGeneratedOn())
        );

        PdfPCell metaCell = new PdfPCell(new Phrase(metaText, subFont));
        metaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        metaCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        metaCell.setPadding(8f);
        metaCell.setBorder(Rectangle.BOX);
        headerTable.addCell(metaCell);


        // Place header
        headerTable.writeSelectedRows(0, -1,
                document.leftMargin(),
                pageSize.getHeight() - 20,
                writer.getDirectContent());

        // === FOOTER ===
        PdfPTable footer = new PdfPTable(3);
        footer.setTotalWidth(pageSize.getWidth() - document.leftMargin() - document.rightMargin());
        footer.setLockedWidth(true);
        footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        try {
            // Load logo from resources
            java.net.URL logoUrl = getClass().getResource("/static/images/Logo.jpg");
            if (logoUrl != null) {
                Image logo = Image.getInstance(logoUrl);
                logo.scaleToFit(100, 50); // Bigger logo size

                PdfPCell logoCell = new PdfPCell(logo, false);
                logoCell.setBorder(Rectangle.NO_BORDER);
                logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                footer.addCell(logoCell);
            } else {
                footer.addCell(new Phrase("", footerFont));
            }
        } catch (Exception e) {
            footer.addCell(new Phrase("", footerFont));
        }

        // Date cell
        PdfPCell dateCell = new PdfPCell(
                new Phrase("Date: " + new SimpleDateFormat("dd-MM-yy").format(new Date()), footerFont));
        dateCell.setBorder(Rectangle.NO_BORDER);
        dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(dateCell);

        // Page number cell
        PdfPCell pageCell = new PdfPCell(new Phrase("Page " + writer.getPageNumber() + " of", footerFont));
        pageCell.setBorder(Rectangle.NO_BORDER);
        pageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        footer.addCell(pageCell);

        // Place footer
        footer.writeSelectedRows(0, -1,
                document.leftMargin(),
                document.bottomMargin(),
                writer.getDirectContent());
    }

	@Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber() - 1), footerFont),

                2, 2, 0);

    }
	 private String safe(String value) {
	        return value != null ? value : "-";
	    }
	
}

 