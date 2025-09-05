package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.audit.AuditLog;
import com.ectd.global.eln.audit.AuditLogDto;

public interface AuditLogDao {
	
	List<AuditLogDto> getAllAuditLogs();
	
    Integer saveAuditLog(AuditLog auditLog);
	
    List<AuditLogDto> getAuditLogsByUserId(int userId);


}
