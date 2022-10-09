package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;

public interface ProjectService {

	ProjectDto getProjectById(Integer projectId);

	List<ProjectDto> getProjects();

	Integer createProject(ProjectRequest projectRequest);

	Integer updateProject(ProjectRequest projectRequest);

	Integer deleteProject(Integer projectId);

}
