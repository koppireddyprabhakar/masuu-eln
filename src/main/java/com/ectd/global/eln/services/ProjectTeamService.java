package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ProjectTeamDto;
import com.ectd.global.eln.request.ProjectTeamRequest;

public interface ProjectTeamService {

	ProjectTeamDto getProjectTeamById(Integer projectTeamId);

	List<ProjectTeamDto> getProjectTeams();

	Integer createProjectTeam(ProjectTeamRequest projectTeamRequest);

	Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest);

	Integer deleteProjectTeam(Integer projectTeamId);
	
}
