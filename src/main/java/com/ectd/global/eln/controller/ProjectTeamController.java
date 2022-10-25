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

import com.ectd.global.eln.dto.ProjectTeamDto;
import com.ectd.global.eln.request.ProjectTeamRequest;
import com.ectd.global.eln.services.ProjectTeamService;

@RestController
@RequestMapping("/project-team")
public class ProjectTeamController extends BaseController {
	
	@Autowired
	ProjectTeamService projectTeamService;
	
	@GetMapping("/get-project-team-by-id")
	public ResponseEntity<ProjectTeamDto> getProjectTeamById(@RequestParam Integer projectTeamId) throws Exception {
		return new ResponseEntity<>(projectTeamService.getProjectTeamById(projectTeamId), HttpStatus.OK);
	}
	
	@GetMapping("/get-project-teams")
	public ResponseEntity<List<ProjectTeamDto>> getProjectTeams() throws Exception {
		return  new ResponseEntity<>(projectTeamService.getProjectTeams(), HttpStatus.OK);
	}
	
	@PostMapping("/create-project-team")
	public ResponseEntity<String> createProjectTeam(@RequestBody ProjectTeamRequest projectTeamRequest) {
		return getResponseEntity(projectTeamService.createProjectTeam(projectTeamRequest), "Project Team Create");
	}
	
	@PutMapping("/update-project-team")
	public ResponseEntity<String> updateProjectTeam(@RequestBody ProjectTeamRequest projectTeamRequest) {
		return getResponseEntity(projectTeamService.updateProjectTeam(projectTeamRequest), "Project Team Update");
	}
	
	@DeleteMapping("/delete-project-team")
	public ResponseEntity<String> deleteProjectTeam(@RequestParam Integer projectTeamId) throws Exception {
		return getResponseEntity(projectTeamService.deleteProjectTeam(projectTeamId), "Project Team Delete");
	}

}
