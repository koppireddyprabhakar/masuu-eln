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

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;
import com.ectd.global.eln.services.TeamsService;

@RestController
@RequestMapping("/teams")
public class TeamsController extends BaseController {

	@Autowired
	TeamsService teamsService;

	@GetMapping("/get-team-by-id")
	public ResponseEntity<TeamsDto> getTeamsById(@RequestParam Integer teamsId) throws Exception {
		return new ResponseEntity<>(teamsService.getTeamsById(teamsId), HttpStatus.OK);
	}

	@GetMapping("/get-teams")
	public ResponseEntity<List<TeamsDto>> getTeamsList() throws Exception {
		return  new ResponseEntity<>(teamsService.getTeamsList(), HttpStatus.OK);
	}

	@PostMapping("/create-team")
	public ResponseEntity<String> createTeams(@RequestBody TeamsRequest teamsRequest) {
		return getResponseEntity(teamsService.createTeams(teamsRequest), "Teams Create");
	}

	@PutMapping("/update-team")
	public ResponseEntity<String> updateTeams(@RequestBody TeamsRequest teamsRequest) {
		return getResponseEntity(teamsService.updateTeams(teamsRequest), "Teams Update");
	}

	@DeleteMapping("/delete-team")
	public ResponseEntity<String> deleteTeams(@RequestBody TeamsRequest teamsRequest) {
		return getResponseEntity(teamsService.deleteTeams(teamsRequest), "Teams Delete");
	}

}
