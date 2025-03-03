package com.ectd.global.eln.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.services.SummaryPdfGenerationService;

@RestController
@RequestMapping("/pdf")
public class SummaryPdfController {

    @Autowired
    private SummaryPdfGenerationService summaryPdfGenerationService;

    @GetMapping("/generate-summary-pdf-and-download")
    public ResponseEntity<byte[]> generateAndDownloadSummaryPdf(@RequestParam Integer projectId) {
        try {
            byte[] pdfBytes = summaryPdfGenerationService.generateSummaryPdf(projectId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "summary.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
