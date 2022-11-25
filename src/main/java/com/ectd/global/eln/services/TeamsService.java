package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;

public interface TeamsService {
	
	TeamsDto getTeamsById(Integer teamsId);
	
	List<TeamsDto> getTeamsList();
	
	Boolean createTeams(TeamsRequest teamsRequest);
	
	Integer updateTeams(TeamsRequest teamsRequest);
	
	Integer deleteTeams(TeamsRequest teamsRequest);

	List<TeamsDto> getFormulationTeams();
	
}
