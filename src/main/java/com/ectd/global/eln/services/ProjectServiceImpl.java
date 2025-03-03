package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ProjectDao;
import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.request.ProjectRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class ProjectServiceImpl implements ProjectService {

	
	@Autowired
	private ElnUtils elnUtils;

	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private UsersDetailsDao usersDetailsDao;
	
	@Autowired
	private EmailNotificationService emailNotificationService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ProjectDto getProjectById(Integer projectId) {
		return projectDao.getProjectById(projectId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProjectDto> getProjects(Integer dosageId, Integer teamId) {
		return projectDao.getProjects(dosageId, teamId);
	}

	@Override
	public Integer createProject(ProjectRequest projectRequest) {
		Integer projectId = projectDao.createProject(projectRequest);

		 UsersDetailsDto creatorDetails = usersDetailsDao.getUsersDetailsById(projectRequest.getInsertUserId());
	     String creatorMailId = creatorDetails.getMailId();

		// Get team members' mail ids
		
		List<String> teamMemberMailIds = projectDao.getTeamMembersByProjectId(projectId);
		// Send email notification to creator and team members
		// Create the email body with HTML formatting for line breaks and bold text
	    String emailBody = String.format(
	        "<html>" +
	        "<body>" +
	        "<p>Dear Team,</p>" +
	        "<p>We are pleased to inform you that a new project has been successfully created. Here are the project details:</p>" +
	        "<ul>" +
	        "<li><b>Project Name:</b> %s</li>" +
	        "<li><b>Project ID:</b> %d</li>" +
	        "<li><b>Team:</b> %s</li>" +
	        "</ul>" +
	        "<p>Thank you for your commitment and effort. Please reach out if you have any questions or need further assistance.</p>" +
	        "<p>Best regards,</p>" +
	        "<p>[Your Team/Company Name]</p>" +
	        "</body>" +
	        "</html>",
	        projectRequest.getProjectName(),
	        projectId,
	        projectRequest.getTeamName()
	    );
	 // Build the email notification using the HTML email body
	    EmailNotification emailNotification = elnUtils.buildEmailNotification(
	        "Project Created",
	        emailBody,
	        creatorMailId,
	        teamMemberMailIds
	    );
		 emailNotificationService.saveEmailNotification(emailNotification);
		 return projectId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateProject(ProjectRequest projectRequest) {
		return projectDao.updateProject(projectRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteProject(ProjectRequest projectRequest) {
		return projectDao.updateProject(projectRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateProjectStatus(ProjectRequest projectRequest) {
	    Integer rowsUpdated = projectDao.updateProjectStatus(projectRequest);
		 
	    if (ProjectRequest.PROJECT_STATUS.ONHOLD.getValue().equals(projectRequest.getStatus())) {
	        // Additional logic for OnHold status
	        // For example, you can send an email notification here
	        UsersDetailsDto creatorDetails = usersDetailsDao.getUsersDetailsById(projectRequest.getInsertUserId());
	        String creatorMailId = creatorDetails.getMailId();
	 
	        List<String> teamMemberMailIds = projectDao.getTeamMembersByProjectId(projectRequest.getProjectId());
	 
	        EmailNotification emailNotification = elnUtils.buildEmailNotification("Project On Hold",
	                "Project with project ID " + projectRequest.getProjectId() + " is now On Hold.", creatorMailId, teamMemberMailIds);
	        emailNotificationService.saveEmailNotification(emailNotification);
	    }
	    return rowsUpdated;
	}
	
	
}
