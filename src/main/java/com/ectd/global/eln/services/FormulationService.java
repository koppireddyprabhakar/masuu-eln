package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;

public interface FormulationService {

	FormulationDto getFormulationById(Integer formulationId);
	
	List<FormulationDto> getFormulations();
	
	Integer createFormulation(FormulationRequest formulationRequest);
	
	Integer updateFormulation(FormulationRequest formulationRequest);
	
	Integer deleteFormulation(Integer formulationId);
	
}
