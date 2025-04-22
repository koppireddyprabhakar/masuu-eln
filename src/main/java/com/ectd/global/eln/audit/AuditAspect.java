package com.ectd.global.eln.audit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ectd.global.eln.repository.AuditLogRepository;
import com.ectd.global.eln.request.Base;
import com.ectd.global.eln.utils.Auditable;
import com.ectd.global.eln.utils.ElnUtils;

@Aspect
@Component
public class AuditAspect {

	@Autowired
	private AuditLogRepository auditLogRepository;
	
	@After("@annotation(auditable)")
	public void logActivity(JoinPoint joinPoint, Auditable auditable) {

		String userName = ElnUtils.DEFAULT_USER_ID;

		if(joinPoint.getArgs().length > 0) {
			Base base = (Base) joinPoint.getArgs()[0];
			userName = base.getInsertUser();

			if(StringUtils.isBlank(userName)) {
				userName = base.getUpdateUser();	
			}

		}

		AuditLog auditLog = new AuditLog();
		auditLog.setUserName(userName);
		auditLog.setAction(auditable.action());
		auditLog.setCreatedDate(ElnUtils.getTimeStamp());

		auditLogRepository.save(auditLog);
	}
	
}
