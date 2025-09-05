package com.ectd.global.eln.services;


import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.utils.AuditReportHeader;

import java.util.List;

public interface AuditReportGenerateService {
    byte[] generateAuditReportFromView(List<AuditLogDto> auditLogs,
                                       String fromDate,
                                       String toDate,
                                       String userName);
}

