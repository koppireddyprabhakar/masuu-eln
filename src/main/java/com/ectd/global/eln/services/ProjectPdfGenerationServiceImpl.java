package com.ectd.global.eln.services;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.ectd.global.eln.dao.AnalysisDao;
import com.ectd.global.eln.dao.ExperimentDao;
import com.ectd.global.eln.dao.ProjectDao;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ProjectDto;
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
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.itextpdf.text.Phrase;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Service
public class ProjectPdfGenerationServiceImpl implements ProjectPdfGenerationService {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ExperimentDao experimentDao;

	@Autowired
	private AnalysisDao analysisDao;

	public static final Font fieldFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	public static final Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	public static final Font subheadingFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

	@Override
	public byte[] generateProjectPdf(Integer projectId) throws Exception {

		ProjectDto projectDto = projectDao.getProjectById(projectId);

		List<ExperimentDto> experiments = experimentDao.getExperimentsByProjectId(projectId);

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			Document document = new Document(PageSize.A3);
            PdfWriter.getInstance(document, outputStream);
			document.open();

			Paragraph proejctHeader = new Paragraph("Project Details", boldFont);
			proejctHeader.setAlignment(Element.ALIGN_CENTER);
			document.add(proejctHeader);

			document.add(new Chunk(new LineSeparator()));

			buildProjectTable(document, projectDto);

			if (!CollectionUtils.isEmpty(experiments)) {
				buildFormulationExperiments(document, experiments);
			}

			List<AnalysisDto> analysisDtos = analysisDao.getAnalysisByProjectId(projectId);
			
			if(!CollectionUtils.isEmpty(analysisDtos)) {
				addSubheading(document, "Analysis Experiment");
				buildAnalysisExperimentTable(document, analysisDtos, projectDto);
			}

			document.close();
			return outputStream.toByteArray();
		}

	}

	private void buildProjectTable(Document document, ProjectDto projectDto) throws DocumentException {
		// Test Results Table
		PdfPTable projectTable = new PdfPTable(9);
		projectTable.setWidthPercentage(100);
		projectTable.setSpacingBefore(10f);
		projectTable.setSpacingAfter(10f);

		// Add table headers
		addTableCell(projectTable, "Project Name", boldFont);
		addTableCell(projectTable, "Product Name", boldFont);
		addTableCell(projectTable, "Code", boldFont);
		addTableCell(projectTable, "Dosage", boldFont);
		addTableCell(projectTable, "Team Name", boldFont);
		addTableCell(projectTable, "Formulation", boldFont);
		addTableCell(projectTable, "Strength", boldFont);
		addTableCell(projectTable, "Creation Date", boldFont);
		addTableCell(projectTable, "Status", boldFont);

		// Add rows with empty cells
		addTableCell(projectTable, projectDto.getProjectName(), fieldFont);
		addTableCell(projectTable, projectDto.getProductName(), fieldFont);
		addTableCell(projectTable, projectDto.getProductCode(), fieldFont);
		addTableCell(projectTable, projectDto.getDosageName(), fieldFont);
		addTableCell(projectTable, projectDto.getTeamName(), fieldFont);
		addTableCell(projectTable, projectDto.getFormulationName(), fieldFont);
		addTableCell(projectTable, projectDto.getStrength(), fieldFont);
		addTableCell(projectTable, projectDto.getInsertDate().toString(), fieldFont);
		addTableCell(projectTable, projectDto.getStatus(), fieldFont);

		document.add(projectTable);
	}

	private void buildFormulationExperiments(Document document, List<ExperimentDto> experiments) {
		
		addSubheading(document, "Formulation Experiment");
		buildExperimentTable(document, experiments);
		
		experiments.stream().forEach(experiment -> {
			List<TestRequestFormDto> testRequestForms = experimentDao.getTRFByExpIds(experiment.getExpId());
			if (!CollectionUtils.isEmpty(testRequestForms)) {
				addSubheading(document, "Test Request Form");
				buildTestRequestTable(document, testRequestForms, experiment);
				testRequestForms.forEach(testRequestFormDto -> {
					if (testRequestFormDto.getAnalysisId() != null) {
						AnalysisDto analysisDto = analysisDao.getAnalysisById(testRequestFormDto.getAnalysisId());
						if(analysisDto != null) {
						addSubheading(document, "Formulation Analysis Experiment");
						buildAnalysisExperimentTable(document, Arrays.asList(analysisDto), experiment.getProject());
						}
					}
				});
			}
		});

	}
	
	private void buildExperimentTable(Document document, List<ExperimentDto> experiments) {

		try {

			PdfPTable experimentTable = new PdfPTable(7);
			experimentTable.setWidthPercentage(100);
			experimentTable.setSpacingBefore(10f);
			experimentTable.setSpacingAfter(10f);

			// Add table headers
			addTableCell(experimentTable, "Exp Name", boldFont);
			addTableCell(experimentTable, "Project Name", boldFont);
			addTableCell(experimentTable, "Batch Size", boldFont);
			addTableCell(experimentTable, "Formulation", boldFont);
			addTableCell(experimentTable, "Strength", boldFont);
			addTableCell(experimentTable, "Creation Date", boldFont);
			addTableCell(experimentTable, "Status", boldFont);

			// Add rows with empty cells
			for(ExperimentDto experiment: experiments) {
				addTableCell(experimentTable, experiment.getExperimentName(), fieldFont);
				addTableCell(experimentTable, experiment.getProject().getProjectName(), fieldFont);
				addTableCell(experimentTable, experiment.getBatchSize(), fieldFont);
				addTableCell(experimentTable, experiment.getProject().getFormulationName(), fieldFont);
				addTableCell(experimentTable, experiment.getProject().getStrength(), fieldFont);
				addTableCell(experimentTable, experiment.getInsertDate().toString(), fieldFont);
				addTableCell(experimentTable, experiment.getStatus(), fieldFont);
			}

			document.add(experimentTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void buildTestRequestTable(Document document, List<TestRequestFormDto> testRequestForms, ExperimentDto experimentDto) {

		try {
			PdfPTable experimentTable = new PdfPTable(7);
			experimentTable.setWidthPercentage(100);
			experimentTable.setSpacingBefore(10f);
			experimentTable.setSpacingAfter(10f);

			// Add table headers
			addTableCell(experimentTable, "Test Name", boldFont);
			addTableCell(experimentTable, "Test ID", boldFont);
			addTableCell(experimentTable, "Project Name", boldFont);
			addTableCell(experimentTable, "Product Name", boldFont);
			addTableCell(experimentTable, "Batch Number", boldFont);
			addTableCell(experimentTable, "Creation Date", boldFont);
			addTableCell(experimentTable, "Test Status", boldFont);

			// Add rows with empty cells
			for(TestRequestFormDto testRequestFormDto: testRequestForms) {
				addTableCell(experimentTable, testRequestFormDto.getTestName(), fieldFont);
				addTableCell(experimentTable, String.valueOf(testRequestFormDto.getTestId()), fieldFont);
				addTableCell(experimentTable, experimentDto.getProject().getProjectName(), fieldFont);
				addTableCell(experimentTable, experimentDto.getProject().getProductName(), fieldFont);
				addTableCell(experimentTable, experimentDto.getBatchNumber(), fieldFont);
				addTableCell(experimentTable, testRequestFormDto.getInsertDate().toString(), fieldFont);
				addTableCell(experimentTable, testRequestFormDto.getStatus(), fieldFont);
			}

			document.add(experimentTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void buildAnalysisExperimentTable(Document document, List<AnalysisDto> analysisDtos, ProjectDto projectDto) {

		try {

			PdfPTable analysisTable = new PdfPTable(7);
			analysisTable.setWidthPercentage(100);
			analysisTable.setSpacingBefore(10f);
			analysisTable.setSpacingAfter(10f);

			// Add table headers
			addTableCell(analysisTable, "Exp Name", boldFont);
			addTableCell(analysisTable, "Project Name", boldFont);
			addTableCell(analysisTable, "Batch Size", boldFont);
			addTableCell(analysisTable, "Formulation", boldFont);
			addTableCell(analysisTable, "Strength", boldFont);
			addTableCell(analysisTable, "Creation Date", boldFont);
			addTableCell(analysisTable, "Status", boldFont);

			// Add rows with empty cells
			for(AnalysisDto analysisDto: analysisDtos) {
			addTableCell(analysisTable, analysisDto.getAnalysisName(), fieldFont);
			addTableCell(analysisTable, projectDto.getProjectName(), fieldFont);
			addTableCell(analysisTable, analysisDto.getBatchSize(), fieldFont);
			addTableCell(analysisTable, projectDto.getFormulationName(), fieldFont);
			addTableCell(analysisTable, projectDto.getStrength(), fieldFont);
			addTableCell(analysisTable, analysisDto.getInsertDate().toString(), fieldFont);
			addTableCell(analysisTable, analysisDto.getStatus(), fieldFont);
			}

			document.add(analysisTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void addTableCell(PdfPTable table, String text, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(text, font));
		cell.setBorderColor(BaseColor.BLACK);
		cell.setPadding(8);
		table.addCell(cell);
	}

	@Override
	public byte[] generateProjectExperimentsPdf(Integer projectId) throws Exception {
	    ProjectDto project = projectDao.getProjectById(projectId);
	    List<ExperimentDto> formulationExperiments = experimentDao.getExperimentsByProjectId(projectId);
	    List<AnalysisDto> analysisExperiments = analysisDao.getAnalysisByProjectId(projectId);

	    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	        Document document = new Document(PageSize.A3);
	        PdfWriter.getInstance(document, outputStream);
	        document.open();

	        Set<Integer> printedAnalysisIds = new HashSet<>();

	        // 1. Project Header
	        addProjectHeader(document, project);

	        // 2. Formulation Experiments Section
	        if (!CollectionUtils.isEmpty(formulationExperiments)) {
	            addSubheading(document, "Formulation Experiments");

	            for (ExperimentDto experiment : formulationExperiments) {
	                addExperimentHeader(document, experiment);
	                addExperimentDetails(document, experimentDao.getExperimentDetailsByExperimentId(experiment.getExpId()));
	                addExcipients(document, experimentDao.getExcipientByExperimentId(experiment.getExpId()));

	                List<TestRequestFormDto> trfs = experimentDao.getTRFByExpIds(experiment.getExpId());
	                addTrfResults(document, trfs);

	                for (TestRequestFormDto trf : trfs) {
	                    Integer analysisId = trf.getAnalysisId();
	                    if (analysisId != null && !printedAnalysisIds.contains(analysisId)) {
	                        AnalysisDto analysisDto = analysisDao.getAnalysisById(analysisId);
	                        if (analysisDto != null) {
	                            addSubheading(document, "Formulation Analysis Experiment");
	                            buildAnalysisExperimentTable(document, Arrays.asList(analysisDto), experiment.getProject());
	                            addAnalysisHeader(document, analysisDto);
	                            addAnalysisExperimentDetails(document, analysisDao.getAnalysisDetailsByAnalysisId(analysisDto.getAnalysisId()));
	                            addAnalysisExcipients(document, analysisDao.getExcipientByAnalysisId(analysisDto.getAnalysisId()));
	                            addTrfResults(document, analysisDao.getTestRequestByAnalysisId(analysisDto.getAnalysisId()));
	                            printedAnalysisIds.add(analysisId); //  avoid reprint later
	                        }
	                    }
	                }

	                document.add(new Chunk(new LineSeparator()));
	                document.add(Chunk.NEWLINE);
	            }
	        }

	        // 3. Remaining Analysis Experiments Section (not already printed via TRF)
	        if (!CollectionUtils.isEmpty(analysisExperiments)) {
	          //  addSubheading(document, "Analysis Experiments");

	            for (AnalysisDto analysis : analysisExperiments) {
	                if (!printedAnalysisIds.contains(analysis.getAnalysisId())) { // skip duplicates
	                    addAnalysisHeader(document, analysis);
	                    addAnalysisExperimentDetails(document, analysisDao.getAnalysisDetailsByAnalysisId(analysis.getAnalysisId()));
	                    addAnalysisExcipients(document, analysisDao.getExcipientByAnalysisId(analysis.getAnalysisId()));
	                    addTrfResults(document, analysisDao.getTestRequestByAnalysisId(analysis.getAnalysisId()));
	                    printedAnalysisIds.add(analysis.getAnalysisId());

	                    document.add(new Chunk(new LineSeparator()));
	                    document.add(Chunk.NEWLINE);
	                }
	            }
	        }

	        // 4. Footer
	        addFooter(document, "Admin", new Date());

	        document.close();
	        return outputStream.toByteArray();
	    }
	}


	private void addAnalysisHeader(Document doc, AnalysisDto analysis) throws DocumentException {
	    addSubheading(doc, "Analysis Experiment ID: " + analysis.getAnalysisId());

	    PdfPTable table = new PdfPTable(2);
	    table.setWidthPercentage(100);
	    table.setWidths(new float[]{2f, 4f});
	    table.setSpacingBefore(5f); //  small gap between heading and table
	    table.setSpacingAfter(10f); //  add space after table

	    addLabelValueRow(table, "Name", analysis.getAnalysisName());
	    addLabelValueRow(table, "Status", analysis.getStatus());
	    addLabelValueRow(table, "Summary", analysis.getSummary());
	    addLabelValueRow(table, "Batch Number", analysis.getBatchNumber());
	    addLabelValueRow(table, "Batch Size", analysis.getBatchSize());
	    addLabelValueRow(table, "Start Date", String.valueOf(analysis.getInsertDate()));
	    addLabelValueRow(table, "End Date", analysis.getUpdateDate() != null ? String.valueOf(analysis.getUpdateDate()) : "-");

	    doc.add(table);
	}

	    private void addProjectHeader(Document doc, ProjectDto project) throws DocumentException {
	        Paragraph header = new Paragraph("Project Summary Report", boldFont);
	        header.setAlignment(Element.ALIGN_CENTER);
	        doc.add(header);
	        doc.add(new Chunk(new LineSeparator()));
	        doc.add(Chunk.NEWLINE);

	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100);
	        table.setWidths(new float[]{2f, 4f});

	        addLabelValueRow(table, "Project Name", project.getProjectName());
	        addLabelValueRow(table, "Project ID", String.valueOf(project.getProjectId()));
	        addLabelValueRow(table, "Start Date", String.valueOf(project.getInsertDate()));
	        addLabelValueRow(table, "Status", project.getStatus());

	        doc.add(table);
	        doc.add(Chunk.NEWLINE);
	    }

	    private void addExperimentHeader(Document doc, ExperimentDto exp) throws DocumentException {
	        addSubheading(doc, "Experiment ID: " + exp.getExpId());

	        PdfPTable table = new PdfPTable(2);
	        table.setWidthPercentage(100);
	        table.setWidths(new float[]{2f, 4f});
	        addLabelValueRow(table, "Name", exp.getExperimentName());
	        addLabelValueRow(table, "Status", exp.getStatus());
	        addLabelValueRow(table, "Summary", exp.getSummary());
	        addLabelValueRow(table, "Batch Number", exp.getBatchNumber());
	        addLabelValueRow(table, "Batch Size", exp.getBatchSize());
	        addLabelValueRow(table, "Start Date", String.valueOf(exp.getInsertDate()));
	        addLabelValueRow(table, "End Date", exp.getUpdateDate() != null ? String.valueOf(exp.getUpdateDate()) : "-");

	        doc.add(table);
	        doc.add(Chunk.NEWLINE);
	    }

	    private void addLabelValueRow(PdfPTable table, String label, String value) {
	        PdfPCell labelCell = new PdfPCell(new Phrase(label, boldFont));
	        labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        labelCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        labelCell.setPadding(5f);
	        table.addCell(labelCell);

	        PdfPCell valueCell = new PdfPCell(new Phrase(value != null ? value : "-", fieldFont));
	        valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        valueCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        valueCell.setPadding(5f);
	        table.addCell(valueCell);
	    }

	    private void addExperimentDetails(Document doc, List<ExperimentDetailsDto> details) throws DocumentException {
	        if (CollectionUtils.isEmpty(details)) return;
	        addSubheading(doc, "Experiment Details");

	        PdfPTable table = new PdfPTable(3);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setWidths(new int[]{1, 8, 1});
	        // Header row
	        Stream.of("Tab Name", "Description", "Status").forEach(header -> {
	            PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setPadding(6f);
	            table.addCell(cell);	            
	            table.setSplitLate(false);
	            table.setSplitRows(true);

	        });

	        // Rows
	        for (ExperimentDetailsDto detail : details) {
	            String decoded = decodeLOBDetails(detail.getFileContent());
	            // Tab Name
	            table.addCell(new Phrase(detail.getName(), fieldFont));
	            // Description
	            PdfPCell descCell = new PdfPCell();
	            descCell.setPadding(5f);
	            descCell.setNoWrap(false);           // allow wrapping
	            descCell.setUseAscender(true);
	            descCell.setUseDescender(true);
	            try {
	                ElementList elements = XMLWorkerHelper.parseToElementList(decoded, null);
	                // Wrap elements into a Phrase, then constrain it
	                Phrase phrase = new Phrase();
	                for (Element e : elements) {
	                    if (e instanceof Paragraph) {
	                        ((Paragraph) e).setAlignment(Element.ALIGN_JUSTIFIED);
	                    }
	                    phrase.add(e);
	                }
	                // Force-fit into column width
	                Paragraph wrapper = new Paragraph(phrase);
	                wrapper.setAlignment(Element.ALIGN_JUSTIFIED);
	                wrapper.setMultipliedLeading(1.2f); // tighter spacing if needed
	                descCell.addElement(wrapper);
	            } catch (Exception ex) {
	                // fallback in case HTML parsing fails
	                Paragraph fallback = new Paragraph(decoded, fieldFont);
	                fallback.setAlignment(Element.ALIGN_JUSTIFIED);
	                descCell.addElement(fallback);
	            }
	            // Ensure descCell respects the column width
	            descCell.setMinimumHeight(20f);
	            descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            table.addCell(descCell);
	            // Status
	            table.addCell(new Phrase(detail.getStatus(), fieldFont));
	        }	       
	        doc.add(table);
	    }

	    
	    private void addExcipients(Document doc, List<ExperimentExcipientDto> excipients) throws DocumentException {
	        if (CollectionUtils.isEmpty(excipients)) return;
	        addSubheading(doc, "Excipients");
	        PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setWidths(new int[]{2, 2, 2, 2, 2, 2});
	        Stream.of("Inward Name", "Grade", "Quantity", "Potency", "Source", "Batch No")
	            .forEach(header -> {
	                PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
	                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                cell.setPadding(6f);
	                table.addCell(cell);
	            });
	        for (ExperimentExcipientDto e : excipients) {
	            table.addCell(new Phrase(e.getMaterialName(), fieldFont));
	            table.addCell(new Phrase(e.getGrade(), fieldFont));
	            table.addCell(new Phrase(String.valueOf(e.getQuantity()), fieldFont));
	            table.addCell(new Phrase(e.getPotency(), fieldFont));
	            table.addCell(new Phrase(e.getSourceName(), fieldFont));
	            table.addCell(new Phrase(e.getBatchNo(), fieldFont));
	        }
	        doc.add(table);
	    }

	    private void addTrfResults(Document doc, List<TestRequestFormDto> trfList) throws DocumentException {
	        if (CollectionUtils.isEmpty(trfList)) return;

	        addSubheading(doc, "Test Request Forms");

	        PdfPTable table = new PdfPTable(4);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setWidths(new float[]{3f, 2f, 2f, 3f});
	        Stream.of("Test Name", "Result", "Status", "Date").forEach(header -> {
	            PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setPadding(6f);
	            table.addCell(cell);
	        });
	        for (TestRequestFormDto t : trfList) {
	            table.addCell(new Phrase(t.getTestName(), fieldFont));
	            table.addCell(new Phrase(t.getTestResult(), fieldFont));
	            table.addCell(new Phrase(t.getStatus(), fieldFont));
	            String formattedDate = (t.getInsertDate() != null) ?
	            	    new SimpleDateFormat("yyyy-MM-dd").format(t.getInsertDate()) : "-";
	            	table.addCell(new Phrase(formattedDate, fieldFont));
	        }
	        doc.add(table);
	    }

	    private void addFooter(Document doc, String printedBy, Date printedDate) throws DocumentException {
	        doc.add(new Paragraph("Printed by: " + printedBy, fieldFont));
	        doc.add(new Paragraph("Printed on: " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(printedDate), fieldFont));
	    }

	    private void addSubheading(Document doc, String subHeading) {
	        try {
	            Paragraph paragraph = new Paragraph(subHeading, subheadingFont);
	            paragraph.setSpacingBefore(10f); // Optional: space before heading
	            paragraph.setSpacingAfter(10f);  //  space after heading
	            paragraph.setAlignment(Element.ALIGN_LEFT);
	            doc.add(paragraph);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	    }



	    private String decodeLOBDetails(String content) {
	        if (content == null || content.trim().isEmpty()) return "[Empty]";

	        try {
	            String cleaned = content.trim();

	            // Case 1: starts with 0x or is valid hex (only hex chars and even length)
	            if (cleaned.startsWith("0x") || cleaned.startsWith("0X")) {
	                cleaned = cleaned.substring(2); // remove 0x
	            }
	            boolean isLikelyHex = cleaned.matches("^[0-9A-Fa-f]+$") && cleaned.length() % 2 == 0;

	            if (isLikelyHex) {
	                byte[] bytes = new BigInteger(cleaned, 16).toByteArray();
	                int startIndex = (bytes.length > 0 && bytes[0] == 0) ? 1 : 0;
	                return new String(bytes, startIndex, bytes.length - startIndex, StandardCharsets.UTF_8);
	            }	          
	            return content;
	        } catch (Exception e) {
	            return "[Invalid LOB]";
	        }
	    }

	    private String stripHtmlTags(String html) {
	        if (html == null) return "";
	        return html.replaceAll("<[^>]*>", "")
	                   .replace("&nbsp;", " ")
	                   .replace("&amp;", "&")
	                   .trim();
	    }
	
	    
	    private void addAnalysisExperimentDetails(Document doc, List<AnalysisDetailsDto> details) throws DocumentException {
	        if (CollectionUtils.isEmpty(details)) return;

	        addSubheading(doc, "Analysis Experiment Details");

	        PdfPTable table = new PdfPTable(3);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setWidths(new int[]{1, 8, 1});
	        // Header row
	        Stream.of("Tab Name", "Description", "Status").forEach(header -> {
	            PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
	            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setPadding(6f);
	            table.addCell(cell);
	            table.setSplitLate(false);
	            table.setSplitRows(true);
	        });
	        // Data rows
	        for (AnalysisDetailsDto detail : details) {
	            // Decode HTML content
	            String decoded = decodeLOBDetails(detail.getFileContent());
	            // Tab Name
	            table.addCell(new Phrase(detail.getName(), fieldFont));
	            // Description (render HTML properly with XMLWorkerHelper)
	            PdfPCell descCell = new PdfPCell();
	            descCell.setPadding(5f);
	         // Apply wrapping & min height here
	         descCell.setNoWrap(false);     // allow text to wrap instead of spilling
	         descCell.setMinimumHeight(20f); // enforce at least one line of height	       
	            try {
	                ElementList elements = XMLWorkerHelper.parseToElementList(decoded, null);
	                for (Element e : elements) {
	                    descCell.addElement(e);
	                }
	            } catch (Exception ex) {
	                // Fallback if parsing fails
	                descCell.addElement(new Phrase(decoded, fieldFont));
	            }
	            table.addCell(descCell);

	            // Status
	            table.addCell(new Phrase(detail.getStatus(), fieldFont));
	        }
	        // Add the table to the document
	        doc.add(table);
	    }

	   	   
	    private void addAnalysisExcipients(Document doc, List<AnalysisExcipientDto> excipients) throws DocumentException {
	        if (CollectionUtils.isEmpty(excipients)) return;
	        addSubheading(doc, "Analysis Excipients");
	        PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100);
	        table.setSpacingBefore(10f);
	        table.setWidths(new int[]{2, 2, 2, 2, 2, 2});
	        Stream.of("Inward Name", "Grade", "Quantity", "Potency", "Source", "Batch No")
	            .forEach(header -> {
	                PdfPCell cell = new PdfPCell(new Phrase(header, boldFont));
	                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                cell.setPadding(6f);
	                table.addCell(cell);
	            });
	        for (AnalysisExcipientDto e : excipients) {
	            table.addCell(new Phrase(e.getMaterialName(), fieldFont));
	            table.addCell(new Phrase(e.getGrade(), fieldFont));
	            table.addCell(new Phrase(String.valueOf(e.getQuantity()), fieldFont));
	            table.addCell(new Phrase(e.getPotency(), fieldFont));
	            table.addCell(new Phrase(e.getSourceName(), fieldFont));
	            table.addCell(new Phrase(e.getBatchNo(), fieldFont));
	        }
	        doc.add(table);
	    }

}



