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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.request.AuditReportRequest;
import com.ectd.global.eln.services.AuditLogService;
import com.ectd.global.eln.services.AuditReportGenerateService;
 
@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;
    
    @Autowired
    private AuditReportGenerateService auditReportService;


    @GetMapping("/get-audit-logs")
    public List<AuditLogDto> getAllLogs() {
        return auditLogService.getAllAuditLogs();
    }
    
	
    @GetMapping("/get-audit-logs-by-userid")
    public List<AuditLogDto> getAuditLogsByUserId(@RequestParam int userId) {
        return auditLogService.getAuditLogsByUserId(userId);
    }
    

    @PostMapping("/audit-report/download")
    public ResponseEntity<byte[]> downloadAuditReport(@RequestBody AuditReportRequest auditReportRequest) {
        List<AuditLogDto> logs = auditReportRequest.getLogs();
        String fromDate = auditReportRequest.getFromDate();
        String toDate = auditReportRequest.getToDate();
        String userName = auditReportRequest.getUserName();

        byte[] pdf = auditReportService.generateAuditReportFromView(logs, fromDate, toDate, userName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Audit_Report.pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

}
