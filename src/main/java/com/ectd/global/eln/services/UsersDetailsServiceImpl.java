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
		public Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest) {
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
			                "<p>Dear %s %s,</p>" +
			                "<p>Your ELN account has been successfully created. Below are your details:</p>" +
			                "<ul>" +
			                "<li><b>Role:</b> %s</li>" +
			                "<li><b>Department:</b> %s</li>" +
			                "</ul>" +
			                "<p>Please log in to your account using your email address: <b>%s</b> and the default password: <b>eln@123456</b>. After logging in, please set a new password for your account and complete your profile.</p>" +
			                "<p>Best regards,</p>" +
			                "<p>[Your Company Name]</p>" +
			                "</body>" +
			                "</html>",
			                usersDetailsRequest.getFirstName(),
			                usersDetailsRequest.getLastName(),
			                usersDetailsRequest.getMailId(),
			                role != null ? role.getRoleName() : "Unknown",
			                department != null ? department.getDepartmentName() : "Unknown"
			                
			    		 );
			     EmailNotification emailNotification = elnUtils.buildEmailNotification(
			 	        "Project Created",
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
		usersDetailsDao.updateUsersDetails(usersDetailsRequest);
		
		if(!CollectionUtils.isEmpty(usersDetailsRequest.getUserTeams())) {
			List<UserTeamRequest> updateUserTeams = usersDetailsRequest.getUserTeams().stream().filter(ut -> !ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());
			List<UserTeamRequest> insertUserTeams = usersDetailsRequest.getUserTeams().stream().filter(ut -> ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());

			if(!CollectionUtils.isEmpty(updateUserTeams)) {
				usersDetailsDao.batchUpdate(updateUserTeams);
			}

			if(!CollectionUtils.isEmpty(insertUserTeams)) {
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
