package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;

public interface ProjectDao {

	ProjectDto getProjectById(Integer projectId);
	
	List<ProjectDto> getProjects(Integer dosageId, Integer teamId);
	
	List<String> getTeamMembersByProjectId(Integer projectId);

	Integer createProject(ProjectRequest projectRequest);
	
	Integer updateProject(ProjectRequest projectRequest);

	Integer deleteProject(Integer projectId);

	Integer updateProjectStatus(ProjectRequest projectRequest);
	
//	Integer createProjectTeam(Integer projectId, Integer teamId);
}
