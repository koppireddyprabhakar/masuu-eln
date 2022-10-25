package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.UserTeamDto;
import com.ectd.global.eln.request.UserTeamRequest;

public interface UserTeamService {

	UserTeamDto getUserTeamByCompositeId(UserTeamRequest userTeamRequest);

	List<UserTeamDto> getUserTeamList();

	Integer createUserTeam(UserTeamRequest userTeamRequest);
	
	Integer updateUserTeam(UserTeamRequest userTeamRequest);

	Integer deleteUserTeam(Integer userTeamId);

}
