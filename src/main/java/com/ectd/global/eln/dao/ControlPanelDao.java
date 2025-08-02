package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.request.ControlPanelRequest;

public interface ControlPanelDao {

    
  //  List<ControlPanelDto> getControlPanels();
    
    Integer createControlPanel(ControlPanelRequest controlPanelRequest);
    
    Integer updateControlPanel(ControlPanelRequest controlPanelRequest);
    
    ControlPanelDto getControlPanel();
    
 // Get the number of users from the control panel
    Integer getNumberOfUsers();
  
}
