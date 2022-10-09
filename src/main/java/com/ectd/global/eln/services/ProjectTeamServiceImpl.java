package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ProjectTeamDao;
import com.ectd.global.eln.dto.ProjectTeamDto;
import com.ectd.global.eln.request.ProjectTeamRequest;

@Service
public class ProjectTeamServiceImpl implements ProjectTeamService {

	@Autowired
	private ProjectTeamDao projectTeamDao; 
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ProjectTeamDto getProjectTeamById(Integer projectTeamId) {
		return projectTeamDao.getProjectTeamById(projectTeamId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProjectTeamDto> getProjectTeams() {
		return projectTeamDao.getProjectTeams();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createProjectTeam(ProjectTeamRequest projectTeamRequest) {
		return projectTeamDao.createProjectTeam(projectTeamRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest) {
		return projectTeamDao.updateProjectTeam(projectTeamRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteProjectTeam(Integer projectTeamId) {
		return projectTeamDao.deleteProjectTeam(projectTeamId);
	}

}
