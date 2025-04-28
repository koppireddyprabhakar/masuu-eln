package com.ectd.global.eln.services;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.CoaReviewDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.utils.PdfFooterEvent;
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
	    public byte[] generatePdf(List<ExperimentDto> experiments, List<TestRequestFormDto> testRequests, List<CoaReviewDto> coaReviewDetails) throws Exception {
	        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            Document document = new Document(PageSize.A4);
	         //  Store PdfWriter instance in a variable
	            PdfWriter writer = PdfWriter.getInstance(document, outputStream);	            
	            //  Set footer before opening document
	            writer.setPageEvent(new PdfFooterEvent());
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
	            TestRequestFormDto testRequestDto = testRequests.get(0);
	            ProjectDto projectDto = experimentDto.getProject();	          
	            List<CoaReviewDto> coaReviewDtoList = coaReviewDetails; 	            	          	            	           	            	         	                      
	            addFieldsToPdf(document,fieldFont, boldFont,
	            		"Test Request ID", String.valueOf(testRequestDto.getTestRequestFormId()),	            		
	            		"Dosage Form", projectDto.getDosageName(),
	            		"Project Name", experimentDto.getProject().getProjectName(),
	                    "Product Name", experimentDto.getProject().getProductName(),
	                    "Product Code", experimentDto.getProject().getProductCode(),
	                    "Market", experimentDto.getProject().getMarkertName(),
	            		"Department",  experimentDto.getDepartmentName(),
	                    "Strength", projectDto.getStrength(),
	                    "Batch No", experimentDto.getBatchNumber(),	                  
	                    "Condition", testRequestDto.getCondition(),
	                    "Stage", String.valueOf(testRequestDto.getStage()),
	                    "Packaging", testRequestDto.getPackaging(),
	                    "Batch Size", String.valueOf(experimentDto.getBatchSize()),
	                    "Quantity", String.valueOf(testRequests.get(0).getQuantity()),  
	                    "Label Claim", testRequestDto.getLabelClaim(),               
	                  //  "Manufacturing Date", experimentDto.getExpStartDate(),
	                   // "Expiry Date", testRequestDto.getExpireDate(),
	                    "Expiry Date", String.valueOf(testRequestDto.getExpireDate()), 
	                    
	                    "Retest Date", "",
	                   // "Sample Quantity", String.valueOf(testRequests.get(0).getQuantity()),  // Assuming only one test request
	                    "A.R. No", String.valueOf(testRequests.get(0).getTestRequestFormId()),  // Assuming only one test request
	                  // "Manufacturer", "", "", null, fieldFont, boldFont);
	                    "Manufacturer", "", "");

	            // Certification Statement
	            Paragraph certificationStatement = new Paragraph("The undersigned hereby certifies the following data to be true specifications of the obtained results of the tests and assays.");
	            document.add(certificationStatement);

	            // Test Results Table
	            PdfPTable resultsTable = new PdfPTable(4);
	            resultsTable.setWidthPercentage(100);
	            resultsTable.setSpacingBefore(10f);
	            resultsTable.setSpacingAfter(10f);
	            resultsTable.setSplitRows(true);
	            resultsTable.setKeepTogether(false);

	            // Add table headers
	            addTableCell(resultsTable, "Test Id", boldFont);
	            addTableCell(resultsTable, "Test Name", boldFont);
	            addTableCell(resultsTable, "Test Description", boldFont);            
	            addTableCell(resultsTable, "Results", boldFont);

	            // Add rows with empty cells
	            for (TestRequestFormDto testRequest : testRequests) {
	                addTableCell(resultsTable, String.valueOf(testRequest.getTestId()), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestName(), fieldFont);
	                addTableCell(resultsTable, testRequest.getDescription(), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestResult(), fieldFont);
	            }

	            document.add(resultsTable);
	            
	         // Compliance Status Section
	            Paragraph complianceSection = new Paragraph("Compliance Status", boldFont);
	            complianceSection.setAlignment(Element.ALIGN_LEFT);
	            document.add(complianceSection);

	            // Extract compliance status from coaReviewDtoList
	            Boolean complianceStatus = (coaReviewDtoList != null && !coaReviewDtoList.isEmpty()) 
	                ? coaReviewDtoList.get(0).isComplianceStatus() 
	                : null; // Handle missing data case

	            // Compliance Status Text (Only one of Compliance or Non-Compliance based on extracted value)
	            Paragraph statusParagraph = new Paragraph();
	            if (Boolean.TRUE.equals(complianceStatus)) { 
	                statusParagraph.add(new Chunk("Compliance"));
	            } else {
	                statusParagraph.add(new Chunk("Non-Compliance"));
	            }
	            
	            statusParagraph.setAlignment(Element.ALIGN_LEFT);
	            document.add(statusParagraph);	       
	            // Signatures Section
	            PdfPTable signaturesTable = new PdfPTable(4);  // Adjusted to 4 columns
	            signaturesTable.setWidthPercentage(100);
	            signaturesTable.setSpacingBefore(10f);
	            signaturesTable.setSpacingAfter(10f);
	            signaturesTable.setSplitRows(true);
	            signaturesTable.setKeepTogether(false);

	            // Add headers
	            addTableCell(signaturesTable, "", boldFont);  // Empty cell
	            addTableCell(signaturesTable, "Prepared by", boldFont);
	            addTableCell(signaturesTable, "Reviewed by", boldFont);
	            addTableCell(signaturesTable, "Approved by", boldFont);

	           
	            addTableCell(signaturesTable, "Signature", fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getPreparedName(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerName(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverName(), fieldFont);
	            
	          
	            addTableCell(signaturesTable, "Date", fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getPreparedDate(), fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getReviewedDate(), fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getApprovedDate(), fieldFont);
	           
	          
	       	       
	            addTableCell(signaturesTable, "Name", fieldFont);	        
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getPreparedName(), fieldFont);            
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerName(), fieldFont);	           
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverName(), fieldFont); 
	            

	            addTableCell(signaturesTable, "Designation", fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getPreparedDesignation(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerDesignation(), fieldFont);
	           addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverDesignation(), fieldFont);
	            

	            document.add(signaturesTable);

	            // Close the document
	            document.close();

	            return outputStream.toByteArray();
	        } catch (DocumentException e) {
	            throw new Exception("Error while generating PDF: " + e.getMessage(), e);
	        }
	    }
	  
	
	    
	    
	    private void addFieldsToPdf(Document document, Font fieldFont, Font boldFont, String... fields) throws DocumentException {
	        PdfPTable table = new PdfPTable(4); // 4 columns
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setSpacingAfter(10f);

	        for (int i = 0; i < fields.length; i += 2) {
	            String label = fields[i];
	            String value = (i + 1 < fields.length) ? fields[i + 1] : ""; // Avoid IndexOutOfBoundsException
	            addTableCell(table, label, value, boldFont, fieldFont);
	        }

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
	        PdfPCell cell = new PdfPCell(new Paragraph(text != null ? text : "", font));
	        cell.setBorderColor(BaseColor.BLACK);
	        cell.setPadding(8);
	        table.addCell(cell);
	    }

	    private void addTableCell(PdfPTable table, Date date, Font font) {
	        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Includes time
	        String formattedDateTime = (date != null) ? dateTimeFormat.format(date) : "";
	        addTableCell(table, formattedDateTime, font);
	    }
	    
	    
	    
	    
	    @Override
	    public byte[] generatePdfForAnalysis(List<AnalysisDto> analysisExperiments,List<TestRequestFormDto> testRequests, List<CoaReviewDto> coaReviewDetails) throws Exception {	    		    	    		    		    	    	
	        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            Document document = new Document(PageSize.A4);
	        //  Store PdfWriter instance in a variable
	            PdfWriter writer = PdfWriter.getInstance(document, outputStream);	            
	            //  Set footer before opening document
	            writer.setPageEvent(new PdfFooterEvent());
	            document.open();
	            Font fieldFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	            // Certificate of Analysis Heading
	            Paragraph coaHeader = new Paragraph("Certificate of Analysis", boldFont);
	            coaHeader.setAlignment(Element.ALIGN_CENTER);
	            document.add(coaHeader);
	            // Underline the heading
	            document.add(new Chunk(new LineSeparator()));	            
	            AnalysisDto analysisDto = analysisExperiments.get(0);
	            TestRequestFormDto testRequestDto = testRequests.get(0);
	            // Assuming AnalysisDto holds a ProjectDto (similar to ExperimentDto)	            
	            ProjectDto projectDto = analysisDto.getProject();	 
	            List<CoaReviewDto> coaReviewDtoList = coaReviewDetails; 	 
	           
	            
	            addFieldsToPdf(document, fieldFont, boldFont,
	                "Test Request ID", String.valueOf(testRequestDto.getTestRequestFormId()),
	                "Dosage Form", projectDto.getDosageName(),
	                "Project Name", projectDto.getProjectName(),
	                "Product Name", projectDto.getProductName(),
	                "Product Code", projectDto.getProductCode(),
                  "Market", analysisDto.getProject().getMarkertName(),
          		"Department",  analysisDto.getDepartmentName(),                
	                "Strength", projectDto.getStrength(),
	                "Batch No", analysisDto.getBatchNumber(),
	                "Condition", testRequestDto.getCondition(),
	                "Stage", String.valueOf(testRequestDto.getStage()),
	                "Packaging", testRequestDto.getPackaging(),
	                "Batch Size", String.valueOf(analysisDto.getBatchSize()),
	                "Quantity", String.valueOf(testRequestDto.getQuantity()),
	                "Label Claim", testRequestDto.getLabelClaim(),
	                "Expiry Date", String.valueOf(testRequestDto.getExpireDate()),
	                "Retest Date", "",
	                "A.R. No", String.valueOf(testRequestDto.getTestRequestFormId()),
	                "Manufacturer", "", ""
	            );
	            // Certification Statement
	            Paragraph certificationStatement = new Paragraph(
	                "The undersigned hereby certifies the following data to be true specifications of the obtained results of the tests and assays."
	            );
	            document.add(certificationStatement);
	            // Test Results Table
	            PdfPTable resultsTable = new PdfPTable(4);
	            resultsTable.setWidthPercentage(100);
	            resultsTable.setSpacingBefore(10f);
	            resultsTable.setSpacingAfter(10f);
	            
	            resultsTable.setSplitRows(true);
	            resultsTable.setKeepTogether(false);

	            // Add table headers
	            addTableCell(resultsTable, "Test Id", boldFont);
	            addTableCell(resultsTable, "Test Name", boldFont);
	            addTableCell(resultsTable, "Test Description", boldFont);
	            addTableCell(resultsTable, "Results", boldFont);
	            
	            // Add rows with test details
	            for (TestRequestFormDto testRequest : testRequests) {
	                addTableCell(resultsTable, String.valueOf(testRequest.getTestId()), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestName(), fieldFont);
	                addTableCell(resultsTable, testRequest.getDescription(), fieldFont);
	                addTableCell(resultsTable, testRequest.getTestResult(), fieldFont);
	            }
	            document.add(resultsTable);
	            
	          
	           
	         // Compliance Status Section
	            Paragraph complianceSectionAnalysis = new Paragraph("Compliance Status", boldFont);
	            complianceSectionAnalysis.setAlignment(Element.ALIGN_LEFT);
	            document.add(complianceSectionAnalysis);

	            // Extract compliance status from coaReviewDtoList
	            Boolean complianceStatus = (coaReviewDtoList != null && !coaReviewDtoList.isEmpty()) 
	                ? coaReviewDtoList.get(0).isComplianceStatus()  // Confirmed correct method
	                : null; // Handle missing data case

	            // Compliance Status Text (Only one of Compliance or Non-Compliance based on extracted value)
	            Paragraph statusParagraph = new Paragraph();
	            if (Boolean.TRUE.equals(complianceStatus)) { 
	                statusParagraph.add(new Chunk("Compliance"));
	            } else {
	                statusParagraph.add(new Chunk("Non-Compliance"));
	            }
	            statusParagraph.setAlignment(Element.ALIGN_LEFT);
	            document.add(statusParagraph);

	            // Signatures Section
	            PdfPTable signaturesTable = new PdfPTable(4); // 4 columns
	            signaturesTable.setWidthPercentage(100);
	            signaturesTable.setSpacingBefore(10f);
	            signaturesTable.setSpacingAfter(10f);
	            signaturesTable.setSplitRows(true);
	            signaturesTable.setKeepTogether(false);


	            // Headers
	            addTableCell(signaturesTable, "", boldFont);  // Empty cell
	            addTableCell(signaturesTable, "Prepared by", boldFont);
	            addTableCell(signaturesTable, "Reviewed by", boldFont);
	            addTableCell(signaturesTable, "Approved by", boldFont);

	            addTableCell(signaturesTable, "Signature", fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getPreparedName(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerName(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverName(), fieldFont);
	            
	          
	            addTableCell(signaturesTable, "Date", fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getPreparedDate(), fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getReviewedDate(), fieldFont);
	            addTableCell(signaturesTable, coaReviewDtoList.get(0).getApprovedDate(), fieldFont);
	           
	          
	       	       
	            addTableCell(signaturesTable, "Name", fieldFont);	        
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getPreparedName(), fieldFont);            
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerName(), fieldFont);	           
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverName(), fieldFont); 
	            

	            addTableCell(signaturesTable, "Designation", fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getPreparedDesignation(), fieldFont);
	            addTableCell(signaturesTable,  coaReviewDtoList.get(0).getReviewerDesignation(), fieldFont);
	           addTableCell(signaturesTable,  coaReviewDtoList.get(0).getApproverDesignation(), fieldFont);

	            document.add(signaturesTable);

	            // Close the document
	            document.close();
	            return outputStream.toByteArray();
	        } catch (DocumentException e) {
	            throw new Exception("Error while generating PDF: " + e.getMessage(), e);
	        }
	    }
	    
}
