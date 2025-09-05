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

//	@Override
//	public List<AuditLogDto> getAllAuditLogs() {
//	    List<AuditLog> logs = auditLogRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
//	    return logs.stream()
//	               .map(log -> {
//	                   AuditLogDto dto = new AuditLogDto();
//	                   dto.setId(log.getId());
//	                   dto.setUserName(log.getUserName());
//	                   dto.setAction(log.getAction());
//	                   dto.setCreatedDate(log.getCreatedDate());
//	                   return dto;
//	               }).collect(Collectors.toList());
//	}
	 @Override
	    public List<AuditLogDto> getAllAuditLogs() {
	        return auditLogDao.getAllAuditLogs();
	    }

	 @Override
	 public List<AuditLogDto> getAuditLogsByUserId(int userId) {
	     return auditLogDao.getAuditLogsByUserId(userId);
	 }

	 
}
