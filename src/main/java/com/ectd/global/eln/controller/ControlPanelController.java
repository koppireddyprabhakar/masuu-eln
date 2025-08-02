package com.ectd.global.eln.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.request.ControlPanelRequest;
import com.ectd.global.eln.services.ControlPanelService;


@RestController
@RequestMapping("/controlpanel")
public class ControlPanelController  extends BaseController {
	
	
	 @Autowired
	    private ControlPanelService controlPanelService;

	   
	 @PostMapping("/create")
	    public ResponseEntity<String> createControlPanel(@RequestBody ControlPanelRequest controlPanelRequest) {
	        return getResponseEntity(controlPanelService.createControlPanel(controlPanelRequest), "Control Panel Create");
	    }

	    @PutMapping("/update")
	    public ResponseEntity<String> updateControlPanel(@RequestBody ControlPanelRequest controlPanelRequest) {
	        return getResponseEntity(controlPanelService.updateControlPanel(controlPanelRequest), "Control Panel Update");
	    }

	    
	    @GetMapping("/get-control-panel")
	    public ResponseEntity<ControlPanelDto> getControlPanel() {
	        ControlPanelDto controlPanel = controlPanelService.getControlPanel();
	        if (controlPanel != null) {
	            return new ResponseEntity<>(controlPanel, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	        }
	    }

	    @GetMapping("/get-number-of-users")
	    public ResponseEntity<Integer> getNumberOfUsers() {
	        Integer numberOfUsers = controlPanelService.getNumberOfUsers();
	        return new ResponseEntity<>(numberOfUsers, HttpStatus.OK);
	    }

}

