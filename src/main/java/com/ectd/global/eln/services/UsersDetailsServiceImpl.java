package com.ectd.global.eln.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UserTeamRequest;
import com.ectd.global.eln.request.UsersDetailsRequest;

@Service
public class UsersDetailsServiceImpl implements UsersDetailsService {

	@Autowired
	private UsersDetailsDao usersDetailsDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UsersDetailsDto getUsersDetailsById(Integer usersDetailsId) {
		return usersDetailsDao.getUsersDetailsById(usersDetailsId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UsersDetailsDto> getUsersDetails(Integer roleId, String departmentName) {
		return usersDetailsDao.getUsersDetails(roleId, departmentName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		return usersDetailsDao.createUsersDetails(usersDetailsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		usersDetailsDao.updateUsersDetails(usersDetailsRequest);
		
		List<UserTeamRequest> updateUserTeams = usersDetailsRequest.getUserTeams().stream().filter(ut -> !ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());
		List<UserTeamRequest> insertUserTeams = usersDetailsRequest.getUserTeams().stream().filter(ut -> ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());

		if(!CollectionUtils.isEmpty(updateUserTeams)) {
			usersDetailsDao.batchUpdate(updateUserTeams);
		}

		if(!CollectionUtils.isEmpty(insertUserTeams)) {
			usersDetailsDao.batchInsert(insertUserTeams, usersDetailsRequest.getUserId());
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		return this.updateUsersDetails(usersDetailsRequest);
	}
	
}
