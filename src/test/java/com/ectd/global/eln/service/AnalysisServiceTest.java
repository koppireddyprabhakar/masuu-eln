package com.ectd.global.eln.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.services.AnalysisService;

@SpringBootTest
public class AnalysisServiceTest {

	@Autowired
	private AnalysisService analysisService;
	
	@Test
	public void getAnalysisDetailByExperimentIdTest() {
		List<AnalysisDto> list = analysisService.getAnalysisDetailByExperimentId(1579);
		
		System.out.println(list);
	}
	
	
}
