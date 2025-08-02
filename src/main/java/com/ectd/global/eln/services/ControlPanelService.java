package com.ectd.global.eln.services;

import java.util.List;
import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.request.ControlPanelRequest;

public interface ControlPanelService {

  //  List<ControlPanelDto> getControlPanels();
    
    ControlPanelDto getControlPanel();

    Integer createControlPanel(ControlPanelRequest controlPanelRequest);


	Integer updateControlPanel(ControlPanelRequest controlPanelRequest);
	
    Integer getNumberOfUsers();

	int getUsersLimit();

	List<String> getAdminEmails();

}
