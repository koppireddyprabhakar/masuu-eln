package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ProjectDao;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	
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
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createProject(ProjectRequest projectRequest) {
		return projectDao.createProject(projectRequest);
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

}
