package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.request.DepartmentRequest;
import com.ectd.global.eln.services.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController {

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/get-department-by-id")
	public ResponseEntity<DepartmentDto> getDepartmentById(@RequestParam Integer departmentId) throws Exception {
		return new ResponseEntity<>(departmentService.getDepartmentById(departmentId), HttpStatus.OK);
	}

	@GetMapping("/get-departments")
	public ResponseEntity<List<DepartmentDto>> getDepartments() throws Exception {
		return  new ResponseEntity<>(departmentService.getDepartments(), HttpStatus.OK);
	}

	@PostMapping("/create-department")
	public ResponseEntity<String> createDepartment(@RequestBody DepartmentRequest departmentRequest) {
		return getResponseEntity(departmentService.createDepartment(departmentRequest), "Department Create");
	}

	@PutMapping("/update-department")
	public ResponseEntity<String> updateDepartment(@RequestBody DepartmentRequest departmentRequest) {
		return getResponseEntity(departmentService.updateDepartment(departmentRequest), "Department Update");
	}

	@DeleteMapping("/delete-department")
	public ResponseEntity<String> deleteDepartment(@RequestParam Integer departmentId) throws Exception {
		return getResponseEntity(departmentService.deleteDepartment(departmentId), "Department Delete");
	}

}
