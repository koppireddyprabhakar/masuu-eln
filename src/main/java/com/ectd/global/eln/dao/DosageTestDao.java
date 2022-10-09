package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.DosageTestDto;
import com.ectd.global.eln.request.DosageTestRequest;

public interface DosageTestDao {

	DosageTestDto getDosageTestById(Integer dosageTestDtoId);

	List<DosageTestDto> getDosageTests();
	
	Integer createDosageTest(DosageTestRequest dosageTestRequest);

	Integer updateDosageTest(DosageTestRequest dosageTestRequest);

	Integer deleteDosageTest(Integer dosageTestId);
	
}
