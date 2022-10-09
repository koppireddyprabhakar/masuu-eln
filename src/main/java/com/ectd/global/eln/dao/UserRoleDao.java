package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.UserRoleDto;
import com.ectd.global.eln.request.UserRoleRequest;

public interface UserRoleDao {
	
	UserRoleDto getUserRoleById(Integer userRoleId);
	List<UserRoleDto> getUserRoles();
	Integer createUserRole(UserRoleRequest userRoleRequest);
	Integer updateUserRole(UserRoleRequest userRoleRequest);
	Integer deleteUserRole(Integer userRoleId);
	
}
