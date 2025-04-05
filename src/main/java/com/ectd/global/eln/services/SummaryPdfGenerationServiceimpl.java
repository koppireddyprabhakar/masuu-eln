package com.ectd.global.eln.services;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SummaryPdfGenerationServiceimpl implements SummaryPdfGenerationService{

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ExperimentService experimentService;


    // Method to generate the PDF with Purpose and Conclusion and Formulation sections
    @Override
    public byte[] generateSummaryPdf(int projectId) throws Exception {
        try {
            // Fetch project details
            ProjectDto projectDto = projectService.getProjectById(projectId);

            // Fetch experiments associated with the project
            List<ExperimentDto> experiments = experimentService.getExperimentsInfo(projectId);

            // Create a new document
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add title
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Font detailFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
            Paragraph title = new Paragraph("Summary", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add project details
            addDetail(document, "Project Name", projectDto.getProjectName(), detailFont);
            addDetail(document, "Product Name", projectDto.getProductName(), detailFont);
            addDetail(document, "Product Code", projectDto.getProductCode(), detailFont);
            addDetail(document, "Dosage Name", projectDto.getDosageName(), detailFont);
            addDetail(document, "Formulation Type", projectDto.getFormulationName(), detailFont);

            // Add Purpose and Conclusion section
            addSection(document, "Purpose and Conclusion", experiments, "Purpose and Conclusion");

            // Add Formulation section
            addSection(document, "Formulation", experiments, "Formulation");

            document.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new Exception("Error while generating PDF: " + e.getMessage(), e);
        }
    }

    // Method to add a section with its details
    private void addSection(Document document, String sectionTitle, List<ExperimentDto> experiments, String detailName) throws Exception {
        Font sectionFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font detailFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        Paragraph section = new Paragraph(sectionTitle, sectionFont);
        section.setAlignment(Element.ALIGN_LEFT);
        document.add(section);
        document.add(Chunk.NEWLINE);

        document.add(Chunk.NEWLINE);
    }

    // Method to add a detail to the document	
    private void addDetail(Document document, String label, String value, Font font) throws Exception {
        Paragraph detail = new Paragraph(label + ": " + value, font);
        detail.setAlignment(Element.ALIGN_LEFT);
        document.add(detail);
    }

}
