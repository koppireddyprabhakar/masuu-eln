package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.UserRoleDao;
import com.ectd.global.eln.dto.UserRoleDto;
import com.ectd.global.eln.request.UserRoleRequest;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public UserRoleDto getUserRoleById(Integer userRoleId) {
		return userRoleDao.getUserRoleById(userRoleId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<UserRoleDto> getUserRoles() {
		return userRoleDao.getUserRoles();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createUserRole(UserRoleRequest userRoleRequest) {
		return userRoleDao.createUserRole(userRoleRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateUserRole(UserRoleRequest userRoleRequest) {
		return userRoleDao.updateUserRole(userRoleRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteUserRole(Integer userRoleId) {
		return userRoleDao.deleteUserRole(userRoleId);
	}

}
