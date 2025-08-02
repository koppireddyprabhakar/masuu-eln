package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.ectd.global.eln.dao.DepartmentDao;
import com.ectd.global.eln.dao.UserRoleDao;
import com.ectd.global.eln.dao.UserTeamDao;
import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.dto.UserRoleDto;
import com.ectd.global.eln.dto.UserTeamDto;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.request.UserTeamRequest;
import com.ectd.global.eln.request.UsersDetailsRequest;
import com.ectd.global.eln.utils.Auditable;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class UsersDetailsServiceImpl implements UsersDetailsService {

	@Autowired
	private UsersDetailsDao usersDetailsDao;
	
	@Autowired
	private ElnUtils elnUtils;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private UserTeamDao userTeamDao;
	
	
	@Autowired
	private EmailNotificationService emailNotificationService;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UsersDetailsDto getUsersDetailsById(Integer usersDetailsId) {
		return usersDetailsDao.getUsersDetailsById(usersDetailsId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UsersDetailsDto> getUsersDetails(Integer roleId, String departmentName) {
		return usersDetailsDao.getUsersDetails(roleId, departmentName);
	}

		@Override
		@Transactional(propagation = Propagation.REQUIRED)
		@Auditable(action = "User Details Added")
		public Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		//  Normalize email at the very beginning
			usersDetailsRequest.setMailId(usersDetailsRequest.getMailId().toLowerCase());
			Integer userId = usersDetailsDao.createUsersDetails(usersDetailsRequest);
			if(userId != null) {
				usersDetailsDao.createUserTeam(usersDetailsRequest, userId);				
				 UsersDetailsDto creatorDetails = usersDetailsDao.getUsersDetailsById(userId);
			     String creatorMailId = creatorDetails.getMailId();
			     DepartmentDto department = departmentDao.getDepartmentById(usersDetailsRequest.getDeptId());
			     UserRoleDto role = userRoleDao.getUserRoleById(usersDetailsRequest.getRoleId());
			     List<String> teamMemberMailIds = new ArrayList<>();
			     String emailBody = String.format(
			    		    "<html>" +
			    		    "<body>" +
			    		    "<p>We are pleased to inform you that your <b>eLN</b> account has been successfully created. Below are your account details:</p>" +
			    		    "<p>&#128313; <b>Role:</b> %s</p>" +
			    		    "<p>&#128313; <b>Department:</b> %s</p>" +
			    		    "<p>&#128313; <b>Email Address:</b> %s</p>" +
			    		    "<p>You can log in to your account using the following credentials:</p>" +
			    		    "<p>&#128231; <b>Username:</b> %s</p>" +
			    		    "<p>&#128273; <b>Default Password:</b> eln@123456</p>" +
			    		    "<p>For security reasons, please log in and change your password immediately.</p>" +
			    		    "<p>If you have any questions or require assistance, feel free to reach out to our support team.</p>" +
			    		    "<p>Best regards,</p>" +
			    		    "<p>[Your Company Name]</p>" +
			    		    "</body>" +
			    		    "</html>",
			    		    role.getRoleName(),
			    		    department.getDepartmentName(),
			    		    usersDetailsRequest.getMailId(),
			    		    usersDetailsRequest.getMailId()
			    		);
			     EmailNotification emailNotification = elnUtils.buildEmailNotification(
				 	        "eLN User Account Created",
				 	        emailBody,
				 	        creatorMailId,
				 	        teamMemberMailIds
				 	    );
			 		 emailNotificationService.saveEmailNotification(emailNotification);
				return true;
			}
			return false;
		}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UsersDetailsDto> getUsersWithCustomRoles(String departmentName) {
		return usersDetailsDao.getUsersWithCustomRoles(departmentName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		//  Normalize email at the very beginning
	    usersDetailsRequest.setMailId(usersDetailsRequest.getMailId().toLowerCase());
	    boolean wasLocked = usersDetailsDao.isUserLocked(usersDetailsRequest.getUserId()); // Check before update
	    usersDetailsDao.updateUsersDetails(usersDetailsRequest);
	    
	    boolean isUnlocked = Boolean.FALSE.equals(usersDetailsRequest.getunLock());

	    if (wasLocked && isUnlocked) {
	        // Fetch user details to get full name
	        UsersDetailsDto userDetails = usersDetailsDao.getUsersDetailsById(usersDetailsRequest.getUserId());
	        String fullName = (userDetails != null) ? userDetails.getFirstName() + " " + userDetails.getLastName() : "User";

	        // Construct email body with personalized name
	        String emailBody = "<html><body>" +
	                "<p>Dear " + fullName + ",</p>" +
	                "<p>Your account has been unlocked successfully. You can now log in.</p>" +
	                "<p>Best regards,</p>" +
	                "<p>[Your Company Name]</p>" +
	                "</body></html>";

	        EmailNotification emailNotification = elnUtils.buildEmailNotification(
	                "Account Unlocked",
	                emailBody,
	                usersDetailsRequest.getMailId(),
	                new ArrayList<>()
	        );

	        try {
	            emailNotificationService.saveEmailNotification(emailNotification);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    if (!CollectionUtils.isEmpty(usersDetailsRequest.getUserTeams())) {
	        List<UserTeamRequest> updateUserTeams = usersDetailsRequest.getUserTeams().stream()
	                .filter(ut -> !ObjectUtils.isEmpty(ut.getUserId()))
	                .collect(Collectors.toList());

	        List<UserTeamRequest> insertUserTeams = usersDetailsRequest.getUserTeams().stream()
	                .filter(ut -> ObjectUtils.isEmpty(ut.getUserId()))
	                .collect(Collectors.toList());

	        if (!CollectionUtils.isEmpty(updateUserTeams)) {
	            usersDetailsDao.batchUpdate(updateUserTeams);
	        }

	        if (!CollectionUtils.isEmpty(insertUserTeams)) {
	            usersDetailsDao.batchInsert(insertUserTeams, usersDetailsRequest.getUserId());
	        }
	    }
	    return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		return this.updateUsersDetails(usersDetailsRequest);
	}
	

       
	
}
