package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UsersDetailsRequest;

public interface UsersDetailsDao {

	UsersDetailsDto getUsersDetailsById(Integer experimentId);

	List<UsersDetailsDto> getUsersDetails();

	Integer createUsersDetails(UsersDetailsRequest experimentRequest);

	Integer updateUsersDetails(UsersDetailsRequest experimentRequest);

	Integer deleteUsersDetails(Integer experimentId);
	
}
