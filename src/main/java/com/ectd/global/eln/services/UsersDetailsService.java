package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UsersDetailsRequest;

public interface UsersDetailsService {

	UsersDetailsDto getUsersDetailsById(Integer usersDetailsId);

	List<UsersDetailsDto> getUsersDetails(Integer roleId, String departmentName);

	Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest);

	Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest);

	Integer deleteUsersDetails(UsersDetailsRequest usersDetailsRequest);
	
	List<UsersDetailsDto> getUsersWithCustomRoles(String departmentName);

	
}
