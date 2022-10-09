package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.UserTeamDto;
import com.ectd.global.eln.request.UserTeamRequest;

public interface UserTeamDao {
	
	UserTeamDto getUserTeamByCompositeId(UserTeamRequest userTeamRequest);

	List<UserTeamDto> getUserTeamList();

	Integer createUserTeam(UserTeamRequest userTeamRequest);
	
	Integer updateUserTeam(UserTeamRequest userTeamRequest);

	Integer deleteUserTeam(Integer userTeamId);

}
