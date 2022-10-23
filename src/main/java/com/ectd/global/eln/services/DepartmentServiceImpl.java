package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.DepartmentDao;
import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.request.DepartmentRequest;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDao departmentDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public DepartmentDto getDepartmentById(Integer departmentId) {
		return departmentDao.getDepartmentById(departmentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DepartmentDto> getDepartments() {
		return departmentDao.getDepartments();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createDepartment(DepartmentRequest departmentRequest) {
		return departmentDao.createDepartment(departmentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateDepartment(DepartmentRequest departmentRequest) {
		return departmentDao.updateDepartment(departmentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDepartment(Integer departmentId) {
		if(this.getDepartmentById(departmentId) != null) {
			return departmentDao.deleteDepartment(departmentId);
		}
		return null;
	}

	@Override
	public List<DepartmentDto> getDepartmentsWithTeams() {
		return departmentDao.getDepartmentsWithTeams();
	}

}
