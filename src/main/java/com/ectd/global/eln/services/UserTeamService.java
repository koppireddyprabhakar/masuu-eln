/*
 * Created on 2022-10-02 ( Date ISO 2022-10-02 - Time 14:32:46 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
*/
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
