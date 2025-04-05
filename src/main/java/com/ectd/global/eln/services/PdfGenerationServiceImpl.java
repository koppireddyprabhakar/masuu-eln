package com.ectd.global.eln.services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {
	 @Override
	    public byte[] generatePdf(List<ExperimentDto> experiments, List<TestRequestFormDto> testRequests) throws Exception {
	        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            Document document = new Document(PageSize.A4);
	            PdfWriter.getInstance(document, outputStream);
	            document.open();

	            Font fieldFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

	            // Certificate of Analysis Heading
	            Paragraph coaHeader = new Paragraph("Certificate of Analysis", boldFont);
	            coaHeader.setAlignment(Element.ALIGN_CENTER);
	            document.add(coaHeader);

	            // Underline the heading
	            document.add(new Chunk(new LineSeparator()));

	            // Product Details Section
	            ExperimentDto experimentDto = experiments.get(0); // Assuming only one experiment in the list

	            addFieldsToPdf(document,
	                    "Product Name", experimentDto.getProject().getProductName(),
	                    "Batch No", experimentDto.getBatchNumber(),
	                    "Manufacturing Date", experimentDto.getExpStartDate(),
	                    "Retest Date", "",
	                    "Sample Quantity", String.valueOf(testRequests.get(0).getQuantity()),  // Assuming only one test request
	                    "A.R. No", String.valueOf(testRequests.get(0).getTestRequestFormId()),  // Assuming only one test request
	                    "Manufacturer", "", "", null, fieldFont, boldFont);

	            // Certification Statement
	            Paragraph certificationStatement = new Paragraph("The undersigned hereby certifies the following data to be true specifications of the obtained results of the tests and assays.");
	            document.add(certificationStatement);

	            // Test Results Table
	            PdfPTable resultsTable = new PdfPTable(3);
	            resultsTable.setWidthPercentage(100);
	            resultsTable.setSpacingBefore(10f);
	            resultsTable.setSpacingAfter(10f);

	            // Add table headers
	            addTableCell(resultsTable, "Test Id", boldFont);
	            addTableCell(resultsTable, "Test Name", boldFont);
	            addTableCell(resultsTable, "Results", boldFont);

	            // Add rows with empty cells
	            for (TestRequestFormDto testRequest : testRequests) {
	                addTableCell(resultsTable, String.valueOf(testRequest.getTestId()), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestName(), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestResult(), fieldFont);
	            }

	            document.add(resultsTable);

	            // Signatures Section
	            PdfPTable signaturesTable = new PdfPTable(4);  // Adjusted to 4 columns
	            signaturesTable.setWidthPercentage(100);
	            signaturesTable.setSpacingBefore(10f);
	            signaturesTable.setSpacingAfter(10f);

	            // Add headers
	            addTableCell(signaturesTable, "", boldFont);  // Empty cell
	            addTableCell(signaturesTable, "Prepared by", boldFont);
	            addTableCell(signaturesTable, "Reviewed by", boldFont);
	            addTableCell(signaturesTable, "Approved by", boldFont);

	            // Add rows with empty cells
	            addTableCell(signaturesTable, "Signature", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);

	            addTableCell(signaturesTable, "Date", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);

	            addTableCell(signaturesTable, "Name", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);

	            addTableCell(signaturesTable, "Designation", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);
	            addTableCell(signaturesTable, "", fieldFont);

	            document.add(signaturesTable);

	            // Close the document
	            document.close();

	            return outputStream.toByteArray();
	        } catch (DocumentException e) {
	            throw new Exception("Error while generating PDF: " + e.getMessage(), e);
	        }
	    }

	    private void addFieldsToPdf(Document document, String label1, String value1,
	                                String label2, String value2,
	                                String label3, String value3,
	                                String label4, String value4,
	                                String label5, String value5,
	                                String label6, String value6,
	                                String label7, String value7,
	                                String label8, String value8,
	                                Font fieldFont, Font boldFont) throws DocumentException {
	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setSpacingAfter(10f);

	        // Add the labels and values to the table
	        addTableCell(table, label1, value1, boldFont, fieldFont);
	        addTableCell(table, label2, value2, boldFont, fieldFont);
	        addTableCell(table, label3, value3, boldFont, fieldFont);
	        addTableCell(table, label4, value4, boldFont, fieldFont);
	        addTableCell(table, label5, value5, boldFont, fieldFont);
	        addTableCell(table, label6, value6, boldFont, fieldFont);
	        addTableCell(table, label7, value7, boldFont, fieldFont);
	        addTableCell(table, label8, value8, boldFont, fieldFont);

	        document.add(table);
	    }

	    private void addTableCell(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
	        PdfPCell labelCell = new PdfPCell(new Paragraph(label, labelFont));
	        labelCell.setBorderColor(BaseColor.BLACK);
	        labelCell.setPadding(8);
	        table.addCell(labelCell);

	        PdfPCell valueCell = new PdfPCell(new Paragraph(value, valueFont));
	        valueCell.setBorderColor(BaseColor.BLACK);
	        valueCell.setPadding(8);
	        table.addCell(valueCell);
	    }

	    private void addTableCell(PdfPTable table, String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
	        cell.setBorderColor(BaseColor.BLACK);
	        cell.setPadding(8);
	        table.addCell(cell);
	    }

}
