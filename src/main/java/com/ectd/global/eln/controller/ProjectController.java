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

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.ProjectRequest;
import com.ectd.global.eln.services.DosageService;
import com.ectd.global.eln.services.ProjectService;
import com.ectd.global.eln.services.TeamsService;

@RestController
@RequestMapping(value = "/project")
public class ProjectController  extends BaseController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	DosageService dosageService;
	
	@Autowired
	TeamsService teamsService;
	
	@GetMapping("/get-project-by-id")
	public ResponseEntity<ProjectDto> getProjectById(@RequestParam Integer projectId) throws Exception {
		return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
	}
	
	@GetMapping("/get-projects")
	public ResponseEntity<List<ProjectDto>> getProjects() throws Exception {
		return  new ResponseEntity<>(projectService.getProjects(null), HttpStatus.OK);
	}
	
	@PostMapping("/create-project")
	public ResponseEntity<String> createProject(@RequestBody ProjectRequest projectRequest) {
		return getResponseEntity(projectService.createProject(projectRequest), "Project Create");
	}
	
	@PutMapping("/update-project")
	public ResponseEntity<String> updateProject(@RequestBody ProjectRequest projectRequest) {
		return getResponseEntity(projectService.updateProject(projectRequest), "Project Update");
	}
	
	@DeleteMapping("/delete-project")
	public ResponseEntity<String> deleteProject(@RequestParam Integer projectId) throws Exception {
		return getResponseEntity(projectService.deleteProject(projectId), "Project Delete");
	}
	
	@GetMapping("/get-dosages-and-formulations")
	public ResponseEntity<List<DosageDto>> getDosagesAndFormulations() throws Exception {
		return  new ResponseEntity<>(dosageService.getDosagesAndFormulations(), HttpStatus.OK);
	}
	
	@GetMapping("/get-formulations-teams")
	public ResponseEntity<List<TeamsDto>> getFormulationTeams() throws Exception {
		return  new ResponseEntity<>(teamsService.getFormulationTeams(), HttpStatus.OK);
	}
}