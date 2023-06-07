package com.ectd.global.eln.services;

import java.util.ArrayList;
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
		
		 EmailNotification emailNotification = elnUtils.buildEmailNotification("Project Created","Project with project ID " + projectId + " has been created successfully.", creatorMailId,teamMemberMailIds);
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
	public Integer inActivateProject(ProjectRequest projectRequest) {
		return projectDao.inActivateProject(projectRequest);
	}
	
}
