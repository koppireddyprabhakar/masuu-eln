package com.ectd.global.eln.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ectd.global.eln.services.ProjectPdfGenerationService;

@SpringBootTest
public class PdfGenerationServiceTest {
	
	@Autowired
	ProjectPdfGenerationService projectPdfGenerationService;
	
	@Test
	public void generateProjectPdfTest() throws Exception {
		projectPdfGenerationService.generateProjectPdf(589);
	}
	

}
