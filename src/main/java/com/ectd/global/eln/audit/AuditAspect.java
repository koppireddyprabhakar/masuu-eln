package com.ectd.global.eln.audit;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.repository.AuditLogRepository;
import com.ectd.global.eln.request.Base;
import com.ectd.global.eln.utils.Auditable;
import com.ectd.global.eln.utils.ElnUtils;

@Aspect
@Component
public class AuditAspect {

	@Autowired
	private AuditLogRepository auditLogRepository;
	
	
	@Autowired
	private UsersDetailsDao  usersDetailsDao;
	
	
	@After("@annotation(auditable)")
	public void logActivity(JoinPoint joinPoint, Auditable auditable) {

		String userName = "ELN";
		Integer userId = 0;


		try {
		    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		    if (attributes != null) {
		        HttpServletRequest request = attributes.getRequest();
		        HttpSession session = request.getSession(false);

		        if (session != null) {
		            Object userIdObj = session.getAttribute("userId");
		            if (userIdObj != null) {
		                userId = (Integer) userIdObj;
		                System.out.println("userId from session in AuditAspect: " + userId);

		                // Fetch the user details
		                UsersDetailsDto userDetails = usersDetailsDao.getUsersDetailsById(userId);
		                if (userDetails != null && StringUtils.isNotBlank(userDetails.getFirstName())) {
		                    userName = userDetails.getFirstName(); // or combine first + last name if needed
		                } else {
		                    System.out.println("Could not fetch user details for userId: " + userId);
		                }
		            } else {
		                System.out.println("userId is null in session");
		            }
		        } else {
		            System.out.println("session is null in AuditAspect");
		        }
		    }
		} catch (Exception ex) {
		    System.out.println("Error in AuditAspect: " + ex.getMessage());
		
		}
		AuditLog auditLog = new AuditLog();
		auditLog.setUserName(userName);
		auditLog.setAction(auditable.action()); // assuming action from annotation
		auditLog.setCreatedDate(ElnUtils.getTimeStamp());
		auditLogRepository.save(auditLog);

	}
}
