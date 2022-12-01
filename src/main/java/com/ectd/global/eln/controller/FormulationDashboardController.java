package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.services.ExperimentService;
import com.ectd.global.eln.services.ProjectService;

@RestController
@RequestMapping("/formulation-dashboard")
public class FormulationDashboardController extends BaseController {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ExperimentService experimentService;

	@GetMapping("/get-projects")
	public ResponseEntity<List<ProjectDto>> getProjects() throws Exception {
		return  new ResponseEntity<>(projectService.getProjects(null, null), HttpStatus.OK);
	}
	
	@GetMapping("/get-projects-by-dosage-or-team-id")
	public ResponseEntity<List<ProjectDto>> getProjectsByDosageId(@RequestParam(required = false) Integer dosageId, 
			@RequestParam(required = false) Integer teamId) {
		return new ResponseEntity<List<ProjectDto>>(projectService.getProjects(dosageId, teamId), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiments")
	public ResponseEntity<List<ExperimentDto>> getExperiments() throws Exception {
		return  new ResponseEntity<>(experimentService.getExperiments(null), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiments-by-user-id")
	public ResponseEntity<List<ExperimentDto>> getExperimentsByUserId(@RequestParam(required = false) Integer userId) {
		return new ResponseEntity<List<ExperimentDto>>(experimentService.getExperiments(userId), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiments-with-project")
	public ResponseEntity<List<ExperimentDto>> getExperimentsWithProject() {
		return new ResponseEntity<List<ExperimentDto>>(experimentService.getExperimentsWithProject(), HttpStatus.OK);
	}
	
	@GetMapping("/get-experiment-by-id")
	public ResponseEntity<List<ExperimentDto>> getExperimentById(@RequestParam Integer experimentId) {
		return new ResponseEntity<List<ExperimentDto>>(experimentService.getExperimentsInfo(experimentId), HttpStatus.OK);
	}
	
}
