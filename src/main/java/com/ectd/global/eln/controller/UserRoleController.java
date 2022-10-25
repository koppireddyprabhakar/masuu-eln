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
import com.ectd.global.eln.dto.UserRoleDto;
import com.ectd.global.eln.request.UserRoleRequest;
import com.ectd.global.eln.services.DepartmentService;
import com.ectd.global.eln.services.UserRoleService;

@RestController
@RequestMapping("/userrole")
public class UserRoleController extends BaseController {
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/get-user-role-by-id")
	public ResponseEntity<UserRoleDto> getUserRoleById(@RequestParam Integer userRoleId) throws Exception {
		return new ResponseEntity<>(userRoleService.getUserRoleById(userRoleId), HttpStatus.OK);
	}
	
	@GetMapping("/get-user-roles")
	public ResponseEntity<List<UserRoleDto>> getUserRoles() throws Exception {
		return  new ResponseEntity<>(userRoleService.getUserRoles(), HttpStatus.OK);
	}
	
	@PostMapping("/create-user-roles")
	public ResponseEntity<String> createUserRole(@RequestBody UserRoleRequest userRoleRequest) {
		return getResponseEntity(userRoleService.createUserRole(userRoleRequest), "User Role Create");
	}
	
	@PutMapping("/update-user-role")
	public ResponseEntity<String> updateUserRole(@RequestBody UserRoleRequest userRoleRequest) {
		return getResponseEntity(userRoleService.updateUserRole(userRoleRequest), "User Role Update");
	}
	
	@DeleteMapping("/delete-user-role")
	public ResponseEntity<String> deleteUserRole(@RequestParam Integer userRoleId) throws Exception {
		return getResponseEntity(userRoleService.deleteUserRole(userRoleId), "User Role Delete");
	}
	
	@GetMapping("/get-departments-with-teams")
	public ResponseEntity<List<DepartmentDto>> getDepartmentsWithTeams() {
		return new ResponseEntity<>(departmentService.getDepartmentsWithTeams(), HttpStatus.OK);
	}
}
