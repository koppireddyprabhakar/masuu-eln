package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.FormulationDao;
import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;

@Service
public class FormulationServiceImpl implements FormulationService {

	@Autowired
	private FormulationDao formulationDao; 

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public FormulationDto getFormulationById(Integer departmentId) {
		return formulationDao.getFormulationById(departmentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<FormulationDto> getFormulations() {
		return formulationDao.getFormulations();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createFormulation(FormulationRequest formulationRequest) {
		return formulationDao.createFormulation(formulationRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateFormulation(FormulationRequest formulationRequest) {
		return formulationDao.updateFormulation(formulationRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteFormulation(Integer formulationId) {
		return formulationDao.deleteFormulation(formulationId);
	}

}
