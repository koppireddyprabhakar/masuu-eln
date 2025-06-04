package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.services.AuditLogService;
import com.ectd.global.eln.services.AuditReportService;
 
@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;
    
    @Autowired
    private AuditReportService auditReportService;


    @GetMapping("/get-audit-logs")
    public List<AuditLogDto> getAllLogs() {
        return auditLogService.getAllAuditLogs();
    }
    
    @PostMapping("/audit-report/download")
    public ResponseEntity<byte[]> downloadAuditReport(@RequestBody List<AuditLogDto> logs) {
        byte[] pdf = auditReportService.generateAuditReportFromView(logs);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Audit_Report.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

}
