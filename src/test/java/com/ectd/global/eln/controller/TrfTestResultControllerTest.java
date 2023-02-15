package com.ectd.global.eln.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;
import com.ectd.global.eln.services.TrfTestResultService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { TrfTestResultController.class })
@ExtendWith(SpringExtension.class)
class TrfTestResultControllerTest {
	@Autowired
	private TrfTestResultController trfTestResultController;

	@MockBean
	private TrfTestResultService trfTestResultService;

	/**
	 * Method under test: {@link TrfTestResultController#getTrfTestResultById(Integer)}
	 */
	@Test
	void testGetTrfTestResultById() throws Exception {
		TrfTestResultDto trfTestResultDto = new TrfTestResultDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultDto.setInsertUser("Insert User");
		trfTestResultDto.setTestId(1);
		trfTestResultDto.setTestStatus("inprogress");
		trfTestResultDto.setTrfId(123);
		trfTestResultDto.setTrfTestId(456);
		trfTestResultDto.setStatus("ACTIVE");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultDto.setUpdateUser("2020-03-01");
		when(this.trfTestResultService.getTrfTestResultById((Integer) any())).thenReturn(trfTestResultDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/trf-test-result/get-trf-test-result-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("trfTestResultId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE\",\"trfTestId\":456,\"trfId\":123,\"testId\":1,\"testStatus\":\"inprogress\"}"));
	}
	/**
	 * Method under test: {@link TrfTestResultController#getTrfTestResultById(Integer)}
	 */
	@Test
	void testGetTrfTestResultById2() throws Exception {
		TrfTestResultDto trfTestResultDto = new TrfTestResultDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultDto.setInsertUser("Insert User");
		trfTestResultDto.setTestId(1);
		trfTestResultDto.setTestStatus("inprogress");
		trfTestResultDto.setTrfId(123);
		trfTestResultDto.setTrfTestId(456);
		trfTestResultDto.setStatus("ACTIVE");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultDto.setUpdateUser("2020-03-01");
		when(this.trfTestResultService.getTrfTestResultById((Integer) any())).thenReturn(trfTestResultDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/trf-test-result/get-trf-test-result-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("trfTestResultId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE\",\"trfTestId\":456,\"trfId\":123,\"testId\":1,\"testStatus\":\"inprogress\"}"));
	}
	/**
	 * Method under test: {@link TrfTestResultController#getTrfTestResults()}
	 */
	@Test
	void testGetTrfTestResults() throws Exception {
		when(this.trfTestResultService.getTrfTestResults()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/trf-test-result/get-trf-test-results");
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
	/**
	 * Method under test: {@link TrfTestResultController#getTrfTestResults()}
	 */
	@Test
	void testGetTrfTestResults2() throws Exception {
		when(this.trfTestResultService.getTrfTestResults()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/trf-test-result/get-trf-test-results");
		getResult.contentType("application/json");
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
	/**
	 * Method under test: {@link TrfTestResultController#createTrfTestResult(TrfTestResultRequest)}
	 */
	@Test
	void testCreateTrfTestResult() throws Exception {
		when(this.trfTestResultService.createTrfTestResult((TrfTestResultRequest) any())).thenReturn(1);

		TrfTestResultRequest trfTestResultRequest = new TrfTestResultRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultRequest.setInsertUser("Insert User");
		trfTestResultRequest.setTestId(1);
		trfTestResultRequest.setTestStatus("inprogress");
		trfTestResultRequest.setTrfId(123);
		trfTestResultRequest.setTrfTestId(456);
		trfTestResultRequest.setStatus("ACTIVE");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(trfTestResultRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/trf-test-result/create-trf-test-result")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"TRF TEST Result Create Successfully\"}"));
	}
	/**
	 * Method under test: {@link TrfTestResultController#createTrfTestResult(TrfTestResultRequest)}
	 */
	@Test
	void testUpdateTrfTestResult() throws Exception {
		when(this.trfTestResultService.updateTrfTestResult((TrfTestResultRequest) any())).thenReturn(1);

		TrfTestResultRequest trfTestResultRequest = new TrfTestResultRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultRequest.setInsertUser("Insert User");
		trfTestResultRequest.setTestId(1);
		trfTestResultRequest.setTestStatus("inprogress");
		trfTestResultRequest.setTrfId(123);
		trfTestResultRequest.setTrfTestId(456);
		trfTestResultRequest.setStatus("ACTIVE");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		trfTestResultRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		trfTestResultRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(trfTestResultRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/trf-test-result/update-trf-test-result")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"TRF TEST Result Update Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link TrfTestResultController#deleteTrfTestResult(Integer)}
	 */
	@Test
	void testDeleteTestRequestForm() throws Exception {
		when(this.trfTestResultService.deleteTrfTestResult((Integer) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
				.delete("/trf-test-result/delete-trf-test-result");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("trfTestResultId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"TRF TEST Result Delete Successfully\"}"));
	}
	/**
	 * Method under test:
	 * {@link TrfTestResultController#deleteTrfTestResult(Integer)}
	 */
	@Test
	void testDeleteTestRequestForm2() throws Exception {
		when(this.trfTestResultService.deleteTrfTestResult((Integer) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
				.delete("/trf-test-result/delete-trf-test-result");
		deleteResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("trfTestResultId",
				String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.trfTestResultController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"TRF TEST Result Delete Successfully\"}"));
	}

}
