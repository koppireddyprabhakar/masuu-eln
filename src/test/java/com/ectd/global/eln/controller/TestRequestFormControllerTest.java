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

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.services.TestRequestFormService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { TestRequestFormController.class })
@ExtendWith(SpringExtension.class)
class TestRequestFormControllerTest {
	@Autowired
	private TestRequestFormController testRequestFormController;

	@MockBean
	private TestRequestFormService testRequestFormService;

	/**
	 * Method under test:
	 * {@link TestRequestFormController#getTestRequestFormById(Integer)}
	 */
	@Test
	void testGetTestRequestFormById() throws Exception {
		TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormDto.setInsertUser("Insert User");
		testRequestFormDto.setAnalysisId(1);
		testRequestFormDto.setCondition(null);
		testRequestFormDto.setExpId(1);
		testRequestFormDto.setExpireDate(null);
		testRequestFormDto.setManufacturingDate(null);
		testRequestFormDto.setPackaging("sss");
		testRequestFormDto.setTestId(1);
		testRequestFormDto.setQuantity(1);
		testRequestFormDto.setStage("pack");
		testRequestFormDto.setStatus("ACTIVE");
		testRequestFormDto.setTestRequestFormId(222);
		testRequestFormDto.setTestRequestFormStatus("success");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormDto.setUpdateUser("2020-03-01");
		when(this.testRequestFormService.getTestRequestFormById((Integer) any())).thenReturn(testRequestFormDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
				.get("/test-request-form/get-test-request-form-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("testRequestFormId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE\",\"testRequestFormId\":222,\"expId\":1,\"projectId\":1,\"analysisId\":1,\"testRequestFormStatus\":\"success\",\"condition\":null,\"stage\":\"pack\",\"packaging\":\"sss\",\"quantity\":1,\"manufacturingDate\":null,\"expireDate\":null}"));
	}

	/**
	 * Method under test:
	 * {@link TestRequestFormController#getTestRequestFormById(Integer)}
	 */
	@Test
	void testGetTestRequestFormById2() throws Exception {
		TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormDto.setInsertUser("Insert User");
		testRequestFormDto.setAnalysisId(1);
		testRequestFormDto.setCondition(null);
		testRequestFormDto.setExpId(1);
		testRequestFormDto.setExpireDate(null);
		testRequestFormDto.setManufacturingDate(null);
		testRequestFormDto.setPackaging("sss");
		testRequestFormDto.setTestId(1);
		testRequestFormDto.setQuantity(1);
		testRequestFormDto.setStage("pack");
		testRequestFormDto.setStatus("ACTIVE");
		testRequestFormDto.setTestRequestFormId(222);
		testRequestFormDto.setTestRequestFormStatus("success");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormDto.setUpdateUser("2020-03-01");
		when(this.testRequestFormService.getTestRequestFormById((Integer) any())).thenReturn(testRequestFormDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
				.get("/test-request-form/get-test-request-form-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("testRequestFormId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE\",\"testRequestFormId\":222,\"expId\":1,\"projectId\":1,\"analysisId\":1,\"testRequestFormStatus\":\"success\",\"condition\":null,\"stage\":\"pack\",\"packaging\":\"sss\",\"quantity\":1,\"manufacturingDate\":null,\"expireDate\":null}"));
	}

	/**
	 * Method under test: {@link TestRequestFormController#getTestRequestForms()}
	 */
	@Test
	void testGetTestRequestForms() throws Exception {
		when(this.testRequestFormService.getTestRequestForms()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/test-request-form/get-test-request-forms");
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link TestRequestFormController#getTestRequestForms()}
	 */
	@Test
	void testGetTestRequestForms2() throws Exception {
		when(this.testRequestFormService.getTestRequestForms()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
				.get("/test-request-form/get-test-request-forms");
		getResult.contentType("application/json");
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test:
	 * {@link TestRequestFormController#createTestRequestForm(TestRequestFormRequest)}
	 */
	@Test
	void testcreateTestRequestForm() throws Exception {
		when(this.testRequestFormService.createTestRequestForm((TestRequestFormRequest) any())).thenReturn(1);

		TestRequestFormRequest testRequestFormRequest = new TestRequestFormRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormRequest.setInsertUser("Insert User");
		testRequestFormRequest.setAnalysisId(1);
		testRequestFormRequest.setCondition(null);
		testRequestFormRequest.setExpId(1);
		testRequestFormRequest.setExpireDate(null);
		testRequestFormRequest.setManufacturingDate(null);
		testRequestFormRequest.setPackaging("sss");
		testRequestFormRequest.setTestId(1);
		testRequestFormRequest.setQuantity(1);
		testRequestFormRequest.setStage("pack");
		testRequestFormRequest.setStatus("ACTIVE");
		testRequestFormRequest.setTestRequestFormId(222);
		testRequestFormRequest.setTestRequestFormStatus("success");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(testRequestFormRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/test-request-form/create-test-request-form").contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"Test Request Form Create Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link TestRequestFormController#updateTestRequestForm(TestRequestFormRequest)}
	 */
	@Test
	void testupdateTestRequestForm() throws Exception {
		when(this.testRequestFormService.updateTestRequestForm((TestRequestFormRequest) any())).thenReturn(1);

		TestRequestFormRequest testRequestFormRequest = new TestRequestFormRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormRequest.setInsertUser("Insert User");
		testRequestFormRequest.setAnalysisId(1);
		testRequestFormRequest.setCondition(null);
		testRequestFormRequest.setExpId(1);
		testRequestFormRequest.setExpireDate(null);
		testRequestFormRequest.setManufacturingDate(null);
		testRequestFormRequest.setPackaging("sss");
		testRequestFormRequest.setTestId(1);
		testRequestFormRequest.setQuantity(1);
		testRequestFormRequest.setStage("pack");
		testRequestFormRequest.setStatus("ACTIVE");
		testRequestFormRequest.setTestRequestFormId(222);
		testRequestFormRequest.setTestRequestFormStatus("success");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		testRequestFormRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		testRequestFormRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(testRequestFormRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/test-request-form/update-test-request-form").contentType(MediaType.APPLICATION_JSON)
				.content(content);
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"Test Request Form Update Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link TestRequestFormController#deleteTestRequestForm(Integer)}
	 */
	@Test
	void testDeleteTestRequestForm() throws Exception {
		when(this.testRequestFormService.deleteTestRequestForm((TestRequestFormRequest) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
				.delete("/test-request-form/delete-test-request-form");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("testRequestFormServiceId",
				String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"Test Request Form Delete Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link TestRequestFormController#deleteTestRequestForm(Integer)}
	 */
	@Test
	void testDeleteTestRequestForm2() throws Exception {
		when(this.testRequestFormService.deleteTestRequestForm((TestRequestFormRequest) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders
				.delete("/test-request-form/delete-test-request-form");
		deleteResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("testRequestFormServiceId",
				String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.testRequestFormController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(
						MockMvcResultMatchers.content().string("{\"data\":\"Test Request Form Delete Successfully\"}"));
	}
}
