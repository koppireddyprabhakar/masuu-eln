package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.request.DepartmentRequest;

public interface DepartmentDao {

	DepartmentDto getDepartmentById(Integer departmentId);
	
	List<DepartmentDto> getDepartments();
	
	Integer createDepartment(DepartmentRequest departmentRequest);
	
	Integer updateDepartment(DepartmentRequest departmentRequest);
	
	Integer deleteDepartment(Integer departmentId);

	List<DepartmentDto> getDepartmentsWithTeams();
	
}
