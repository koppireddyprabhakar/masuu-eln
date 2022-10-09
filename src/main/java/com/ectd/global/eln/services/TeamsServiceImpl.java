package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.TeamsDao;
import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;

@Service
public class TeamsServiceImpl implements TeamsService {

	@Autowired
	private TeamsDao teamsDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public TeamsDto getTeamsById(Integer teamsId) {
		return teamsDao.getTeamsById(teamsId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TeamsDto> getTeamsList() {
		return teamsDao.getTeamsList();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createTeams(TeamsRequest teamsRequest) {
		return teamsDao.createTeams(teamsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTeams(TeamsRequest teamsRequest) {
		return teamsDao.updateTeams(teamsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTeams(Integer teamsId) {
		return teamsDao.deleteTeams(teamsId);
	}

	@Override
	public List<TeamsDto> getFormulationTeams() {
		return teamsDao.getFormulationTeams();
	}

}
