package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;

public interface TeamsDao {

	TeamsDto getTeamsById(Integer teamsId);
	
	List<TeamsDto> getTeamsList();
	
	Integer createTeams(TeamsRequest teamsRequest);
	
	Integer updateTeams(TeamsRequest teamsRequest);
	
	Integer deleteTeams(Integer teamsId);
	
	List<TeamsDto> getFormulationTeams();
}
