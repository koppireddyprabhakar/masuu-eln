package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ProjectTeamDto;
import com.ectd.global.eln.request.ProjectTeamRequest;

public interface ProjectTeamDao {
	
	ProjectTeamDto getProjectTeamById(Integer projectTeamId);

	List<ProjectTeamDto> getProjectTeams();

	Integer createProjectTeam(ProjectTeamRequest projectTeamRequest);

	Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest);

	Integer deleteProjectTeam(Integer projectTeamId);

}
