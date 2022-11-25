package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamDosage;
import com.ectd.global.eln.request.TeamsRequest;

public interface TeamsDao {

	TeamsDto getTeamsById(Integer teamsId);
	
	List<TeamsDto> getTeamsList();
	
	Boolean createTeams(TeamsRequest teamsRequest);
	
	Integer updateTeams(TeamsRequest teamsRequest);
	
	Integer deleteTeams(Integer teamsId);
	
	List<TeamsDto> getFormulationTeams();
	
	int[] batchInsert(List<TeamDosage> teamDosages, Integer teamId);
	
	int[] batchUpdate(List<TeamDosage> teamDosages);
}
