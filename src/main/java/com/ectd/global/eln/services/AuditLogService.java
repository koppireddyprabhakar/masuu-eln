package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.audit.AuditLogDto;

public interface AuditLogService {
	
	List<AuditLogDto> getAllAuditLogs();
	
	List<AuditLogDto> getAuditLogsByUserId(int userId);

}
