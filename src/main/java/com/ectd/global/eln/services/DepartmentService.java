package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.request.DepartmentRequest;

public interface DepartmentService {
	
	DepartmentDto getDepartmentById(Integer departmentId);
	
	List<DepartmentDto> getDepartments();
	
	Integer createDepartment(DepartmentRequest departmentRequest);
	
	Integer updateDepartment(DepartmentRequest departmentRequest);
	
	Integer deleteDepartment(Integer departmentId);

	List<DepartmentDto> getDepartmentsWithTeams();
	
}
