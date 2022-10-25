package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.request.DosageRequest;

public interface DosageDao {

	DosageDto getDosageById(Integer dosageId);
	
	List<DosageDto> getDosages();
	
	Integer createDosage(DosageRequest dosageRequest);
	
	Integer updateDosage(DosageRequest dosageRequest);
	
	Integer deleteDosage(Integer dosageId);
	
	List<DosageDto> getDosagesAndFormulations();
	
	Boolean saveDosageWithFormulations(DosageRequest dosageRequest);
	
	Boolean updateDosageWithFormulations(DosageRequest dosageRequest);

}
