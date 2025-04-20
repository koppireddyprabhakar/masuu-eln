package com.ectd.global.eln.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.request.DosageRequest;
import com.ectd.global.eln.services.DosageService;

@SpringBootTest
public class DosageServiceTest {

	@Autowired
	private DosageService dosageService;
	
	@Test
	public void getDosagesAndFormulationsTest() {
		List<DosageDto> tst = dosageService.getDosagesAndFormulations();
		System.out.println(tst);
	}
	
	@Test
	public void getDosageByIdTest() {
		DosageDto tst = dosageService.getDosageById(35);
		System.out.println(tst);
	}
	
	@Test
	public void createDosageTest() {
		
		DosageRequest req = new DosageRequest();
		req.setDosageName("AOP_NAME_test");
		req.setInsertUser("Siva1");
		
		Integer tst = dosageService.createDosage(req);
		System.out.println(tst);
	}
	
}
