package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.services.ExperimentService;
import com.ectd.global.eln.services.PdfGenerationService;
import com.ectd.global.eln.services.ProjectPdfGenerationService;

@RestController
@RequestMapping("/pdf")
public class PdfExportController {

    @Autowired
    private PdfGenerationService pdfGenerationService;
    
    @Autowired
    private ProjectPdfGenerationService projectPdfGenerationService;

    @Autowired
    private ExperimentService experimentService;

    @GetMapping("/generate-coa-pdf-and-download")
    public ResponseEntity<byte[]> generateAndDownloadPdf(@RequestParam Integer experimentId) {
        try {
            // Fetch experiment information
            List<ExperimentDto> experiments = experimentService.getExperimentsInfo(experimentId);
            
            // Fetch test requests
            List<TestRequestFormDto> testRequests = experimentService.getTRFByExpIds(experimentId);

            byte[] pdfBytes = pdfGenerationService.generatePdf(experiments, testRequests);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "COA_GENERATION_FORM.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-project-pdf")
    public ResponseEntity<byte[]> generateProjectPdf(@RequestParam Integer projectId) {
        try {

            byte[] pdfBytes = projectPdfGenerationService.generateProjectPdf(projectId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "PROJECT_DETAILS_"+projectId+".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}