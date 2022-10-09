package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.List;

import com.ectd.global.eln.request.Base;

public class DepartmentDto extends Base implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer deptId;
	private String departmentName;
	
	private List<TeamsDto> teamsDtos;

	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<TeamsDto> getTeamsDtos() {
		return teamsDtos;
	}
	public void setTeamsDtos(List<TeamsDto> teamsDtos) {
		this.teamsDtos = teamsDtos;
	}
	
}
