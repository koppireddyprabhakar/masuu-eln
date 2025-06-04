package com.ectd.global.eln.services;


import com.ectd.global.eln.audit.AuditLogDto;

import java.util.List;

public interface AuditReportService {
    byte[] generateAuditReportFromView(List<AuditLogDto> auditLogs);
}
