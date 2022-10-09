package com.ectd.global.eln.request;

import java.io.Serializable;

public class UserRoleRequest extends Base implements Serializable{
	
	private static final long serialVersionUID = 5894763708614310114L;

	private Integer roleId;
    private String roleName;
    
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
