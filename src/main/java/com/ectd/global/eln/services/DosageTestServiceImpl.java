package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.DosageTestDao;
import com.ectd.global.eln.dto.DosageTestDto;
import com.ectd.global.eln.request.DosageTestRequest;

@Service
public class DosageTestServiceImpl implements DosageTestService {

	@Autowired
	private DosageTestDao dosageTestDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public DosageTestDto getDosageTestById(Integer dosageTestDtoId) {
		return dosageTestDao.getDosageTestById(dosageTestDtoId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DosageTestDto> getDosageTests() {
		return dosageTestDao.getDosageTests();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createDosageTest(DosageTestRequest dosageTestRequest) {
		return dosageTestDao.createDosageTest(dosageTestRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateDosageTest(DosageTestRequest dosageTestRequest) {
		return dosageTestDao.updateDosageTest(dosageTestRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDosageTest(Integer dosageTestId) {
		return dosageTestDao.deleteDosageTest(dosageTestId);
	}
	
}
