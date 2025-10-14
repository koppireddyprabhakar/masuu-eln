package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ectd.global.eln.audit.AuditLogDto;
import com.ectd.global.eln.dao.AuditLogDao;

@Service
public class AuditLogServiceImpl implements AuditLogService {
	@Autowired
    private AuditLogDao auditLogDao ;

	 @Override
	    public List<AuditLogDto> getAllAuditLogs() {
	        return auditLogDao.getAllAuditLogs();
	    }

	 @Override
	 public List<AuditLogDto> getAuditLogsByUserId(int userId) {
	     return auditLogDao.getAuditLogsByUserId(userId);
	 }

	 
}
