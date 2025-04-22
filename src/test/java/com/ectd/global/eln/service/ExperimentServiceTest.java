package com.ectd.global.eln.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.services.ExperimentService;

@SpringBootTest
public class ExperimentServiceTest {
	
	@Autowired
	ExperimentService experimentService;
	
	@Test
	public void getExperimentsByProjectIdTest() {
		List<ExperimentDto> list = experimentService.getExperimentsByProjectId(589);
		System.out.println(list);
	}

}
