package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.UsersDetailsDto;
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
	public List<UsersDetailsDto> getUsersDetails() {
		return usersDetailsDao.getUsersDetails();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		return usersDetailsDao.createUsersDetails(usersDetailsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		return usersDetailsDao.updateUsersDetails(usersDetailsRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteUsersDetails(Integer usersDetailsId) {
		return usersDetailsDao.deleteUsersDetails(usersDetailsId);
	}
	
}
