package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.services.AuditLogService;

@RestController
@RequestMapping("/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/get-audit-logs")
    public List<AuditLogDto> getAllLogs() {
        return auditLogService.getAllAuditLogs();
    }
}
