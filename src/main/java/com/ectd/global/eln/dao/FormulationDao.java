package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;

public interface FormulationDao {

	FormulationDto getFormulationById(Integer departmentId);
	
	List<FormulationDto> getFormulations();
	
	Integer createFormulation(FormulationRequest formulationRequest);
	
	Integer updateFormulation(FormulationRequest formulationRequest);
	
	Integer deleteFormulation(Integer formulationId);
	
}
