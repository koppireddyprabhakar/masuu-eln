package com.ectd.global.eln.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.TeamsDao;
import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamDosage;
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
	public Boolean createTeams(TeamsRequest teamsRequest) {
		return teamsDao.createTeams(teamsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateTeams(TeamsRequest teamsRequest) {
		teamsDao.updateTeams(teamsRequest);

		List<TeamDosage> updateTeamDosages = teamsRequest.getTeamDosages().stream().filter(td -> !(ObjectUtils.isEmpty(td.getTeamId())) ).collect(Collectors.toList());
		List<TeamDosage> insertTeamDosages = teamsRequest.getTeamDosages().stream().filter(td -> ObjectUtils.isEmpty(td.getTeamId())).collect(Collectors.toList());

		if(!CollectionUtils.isEmpty(updateTeamDosages)) {
			teamsDao.batchUpdate(updateTeamDosages);
		}

		if(!CollectionUtils.isEmpty(insertTeamDosages)) {
			teamsDao.batchInsert(insertTeamDosages, teamsRequest.getTeamId());
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteTeams(TeamsRequest teamsRequest) {
		return this.updateTeams(teamsRequest);
	}

	@Override
	public List<TeamsDto> getFormulationTeams() {
		return teamsDao.getFormulationTeams();
	}

}
