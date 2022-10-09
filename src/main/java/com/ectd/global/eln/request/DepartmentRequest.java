package com.ectd.global.eln.request;

import java.io.Serializable;

public class DepartmentRequest extends Base implements Serializable {
	
	private static final long serialVersionUID = -7936962582165912542L;
	private Integer deptId;
    private String departmentName;     
    
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
	
}
