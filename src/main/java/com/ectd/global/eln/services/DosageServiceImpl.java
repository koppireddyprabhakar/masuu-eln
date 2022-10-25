package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.DosageDao;
import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.request.DosageRequest;

@Service
public class DosageServiceImpl implements DosageService {

	@Autowired
	private DosageDao dosageDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public DosageDto getDosageById(Integer dosageId) {
		return dosageDao.getDosageById(dosageId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DosageDto> getDosages() {
		return dosageDao.getDosages();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createDosage(DosageRequest dosageRequest) {
		return dosageDao.createDosage(dosageRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateDosage(DosageRequest dosageRequest) {
		return dosageDao.updateDosage(dosageRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteDosage(Integer dosageId) {
		
		if(this.getDosageById(dosageId) != null) {
			return dosageDao.deleteDosage(dosageId);
		}

		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DosageDto> getDosagesAndFormulations() {
		return dosageDao.getDosagesAndFormulations();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean saveDosageWithFormulations(DosageRequest dosageRequest) {
		return dosageDao.saveDosageWithFormulations(dosageRequest);
	}

	@Override
	public Boolean updateDosageWithFormulations(DosageRequest dosageRequest) {
		return dosageDao.updateDosageWithFormulations(dosageRequest);
	}

}
