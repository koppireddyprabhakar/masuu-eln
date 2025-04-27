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

			Document document = new Document(PageSize.A4);
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
						addSubheading(document, "Formulation Analysis Experiment");
						buildAnalysisExperimentTable(document, Arrays.asList(analysisDto), experiment.getProject());
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

	private void addSubheading(Document document, String subHeading) {
		try {
			Chunk subheadingChunk = new Chunk(subHeading, subheadingFont);
			subheadingChunk.setUnderline(1f, -2f); // Adjust thickness and position as needed
			document.add(new Paragraph(subheadingChunk));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
