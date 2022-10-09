package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;

public interface TeamsService {
	
	TeamsDto getTeamsById(Integer teamsId);
	
	List<TeamsDto> getTeamsList();
	
	Integer createTeams(TeamsRequest teamsRequest);
	
	Integer updateTeams(TeamsRequest teamsRequest);
	
	Integer deleteTeams(Integer teamsId);

	List<TeamsDto> getFormulationTeams();
	
}
