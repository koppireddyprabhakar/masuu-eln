package com.ectd.global.eln.audit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ectd.global.eln.dao.AuditLogDao;
import com.ectd.global.eln.dao.LoginDao;
import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.LoginRequest;
import com.ectd.global.eln.security.JwtUtil;
import com.ectd.global.eln.utils.Auditable;
import com.ectd.global.eln.utils.ElnUtils;
import com.ectd.global.eln.utils.IpUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Aspect
@Component
public class AuditAspect {

	private static final Logger logger = LogManager.getLogger(AuditAspect.class);
	
	@Autowired
	private AuditLogDao auditLogDao;
	
	@Autowired
	private UsersDetailsDao  usersDetailsDao;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private JwtUtil jwtUtil;

	@After("@annotation(auditable)")
	public void logActivity(JoinPoint joinPoint, Auditable auditable) {

		String userName = "ELN";
		String ipAddress = "UNKNOWN";
		Integer userId = 0;
		try {
		    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		    if (attributes != null) {
		        HttpServletRequest request = attributes.getRequest();
		       
                ipAddress = IpUtils.getClientIp(request);     
		            }		       			  		
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof LoginDto) {
                LoginDto loginDto = (LoginDto) principal;
                userId = loginDto.getUserId();
                userName = loginDto.getFirstName() + " " + loginDto.getLastName();

            }
        }
        if (userId == 0 && joinPoint.getArgs() != null) {
            for (Object arg : joinPoint.getArgs()) {
                if (arg instanceof LoginRequest) {
                    LoginRequest loginRequest = (LoginRequest) arg;
                    String mailId = loginRequest.getMailId();
                    if (mailId != null && !mailId.isEmpty()) {                     
                    	  LoginDto loginDto = loginDao.getUserDetails(mailId);
                        if (loginDto != null) {
                            userId = loginDto.getUserId();
                            userName = loginDto.getFirstName() + " " + loginDto.getLastName();
                        }
                    }
                }
            }
        }              
        if (userId > 0) {
            UsersDetailsDto userDetails = usersDetailsDao.getUsersDetailsById(userId);
            if (userDetails != null && StringUtils.isNotBlank(userDetails.getFirstName())) {
                userName = userDetails.getFirstName();
                if (StringUtils.isNotBlank(userDetails.getLastName())) {
                    userName += " " + userDetails.getLastName();
                }
            }
        }

    } catch (Exception ex) {
        logger.error("Error in AuditAspect", ex);
    }

				AuditLog auditLog = new AuditLog();
				auditLog.setUserName(userName);
				auditLog.setAction(auditable.action());
				auditLog.setUserId(userId);
				auditLog.setEventType(auditable.eventType());  
				auditLog.setModuleSection(auditable.moduleSection());
				auditLog.setIpAddress(ipAddress);           
				auditLog.setCreatedDate(ElnUtils.getTimeStamp());					
				 if (userId != null && userId > 0) {
			            auditLogDao.saveAuditLog(auditLog);
			        } else {
			            logger.warn("Skipping audit log: No valid user found for action {}", auditable.action());
			        }

	}
	
}
