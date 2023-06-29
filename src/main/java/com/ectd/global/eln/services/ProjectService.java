package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;

public interface ProjectService {

	ProjectDto getProjectById(Integer projectId);

	List<ProjectDto> getProjects(Integer dosageId, Integer teamId);

	Integer createProject(ProjectRequest projectRequest);

	Integer updateProject(ProjectRequest projectRequest);

	Integer deleteProject(ProjectRequest projectRequest);

	Integer updateProjectStatus(ProjectRequest projectRequest);

}
