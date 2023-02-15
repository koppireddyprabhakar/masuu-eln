package com.ectd.global.eln.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.services.AnalysisServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { AnalysisExperimentController.class })
@ExtendWith(SpringExtension.class)
class AnalysisExperimentControllerTest {
	@Autowired
	private AnalysisExperimentController analysisExperimentController;

	@MockBean
	private AnalysisServiceImpl analysisService;

	/**
	 * Method under test: {@link AnalysisExperimentController#createAnalysisExperiment(AnalysisRequest)}
	 */
	@Test
	void testcreateAnalysisExperiment() throws Exception {
		when(this.analysisService.createAnalysis((AnalysisRequest) any())).thenReturn(1);

		AnalysisRequest analysisRequest = new AnalysisRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		analysisRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		analysisRequest.setInsertUser("Insert User");
		analysisRequest.setAnalysisDetailsList(null);
		analysisRequest.setAnalysisId(1);
		analysisRequest.setAnalysisName("Requirement");
		analysisRequest.setExcipients(null);
		analysisRequest.setTeamId(2);
		analysisRequest.setProjectId(3);
		analysisRequest.setStatus("INACTIVE");
		analysisRequest.setSummary("FILLIN");
		analysisRequest.setTeamId(45);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		analysisRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		analysisRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(analysisRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/analysisExperiment/create-analysis-experiment")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.analysisExperimentController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Analysis Create Successfully\"}"));
	}
	/**
	 * Method under test: {@link AnalysisExperimentController#createAnalysisExperiment(AnalysisRequest)}
	 */
	@Test
	void testupdateAnalysisExperiment() throws Exception {
		when(this.analysisService.createAnalysis((AnalysisRequest) any())).thenReturn(1);

		AnalysisRequest analysisRequest = new AnalysisRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		analysisRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		analysisRequest.setInsertUser("Insert User");
		analysisRequest.setAnalysisDetailsList(null);
		analysisRequest.setAnalysisId(1);
		analysisRequest.setAnalysisName("Requirement");
		analysisRequest.setExcipients(null);
		analysisRequest.setTeamId(2);
		analysisRequest.setProjectId(3);
		analysisRequest.setStatus("INACTIVE");
		analysisRequest.setSummary("FILLIN");
		analysisRequest.setTeamId(45);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		analysisRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		analysisRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(analysisRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/analysisExperiment/update-analysis-experiment")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.analysisExperimentController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Analysis Create Successfully\"}"));
	}


}
