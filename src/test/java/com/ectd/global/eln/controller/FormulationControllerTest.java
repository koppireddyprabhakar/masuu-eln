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

import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;
import com.ectd.global.eln.services.ExperimentService;
import com.ectd.global.eln.services.FormulationService;
import com.ectd.global.eln.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { FormulationController.class })
@ExtendWith(SpringExtension.class)

class FormulationControllerTest {

	@Autowired
	private FormulationController formulationController;

	@MockBean
	private FormulationService formulationService;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private ExperimentService experimentService;

	/**
	 * Method under test: {@link FormulationController#getFormulationById(Integer)}
	 */
	@Test
	void testGetFormulationById() throws Exception {
		FormulationDto formulationDto = new FormulationDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		formulationDto.setInsertUser("Insert User");
		formulationDto.setFormulationId(123);
		formulationDto.setFormulationName("Formulation Name");
		formulationDto.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		formulationDto.setUpdateUser("2020-03-01");
		formulationDto.setDosageId(121);
		when(this.formulationService.getFormulationById((Integer) any())).thenReturn(formulationDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/formulation/get-formulation-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("formulationId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
								+ "\",\"formulationId\":123,\"formulationName\":\"Formulation Name\",\"dosageId\":121}"));

	}

	/**
	 * Method under test: {@link ProductController#getProductById(Integer)}
	 */
	@Test
	void testGetFormulationById2() throws Exception {
		FormulationDto formulationDto = new FormulationDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		formulationDto.setInsertUser("Insert User");
		formulationDto.setFormulationId(123);
		formulationDto.setFormulationName("Formulation Name");
		formulationDto.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		formulationDto.setUpdateUser("2020-03-01");
		formulationDto.setDosageId(121);
		when(this.formulationService.getFormulationById((Integer) any())).thenReturn(formulationDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/formulation/get-formulation-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("formulationId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
								+ "\",\"formulationId\":123,\"formulationName\":\"Formulation Name\",\"dosageId\":121}"));

	}

	/**
	 * Method under test: {@link FormulationController#getFormulations()}
	 */
	@Test
	void testgetFormulations() throws Exception {
		when(this.formulationService.getFormulations()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/formulation/get-formulations");
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link FormulationController#getFormulations()}
	 */
	@Test
	void testgetFormulations2() throws Exception {
		when(this.formulationService.getFormulations()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/formulation/get-formulations");
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test:
	 * {@link FormulationController#createFormulation(FormulationRequest)}
	 */
	@Test
	void testCreateFormulation() throws Exception {
		when(this.formulationService.createFormulation((FormulationRequest) any())).thenReturn(1);

		FormulationRequest formulationRequest = new FormulationRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		formulationRequest.setInsertUser("Insert User");
		formulationRequest.setFormulationName("wwww");
		formulationRequest.setFormulationId(null);
		formulationRequest.setStatus("Product Name");
		formulationRequest.setDosageId(1);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		formulationRequest.setUpdateUser("ELN");
		String content = (new ObjectMapper()).writeValueAsString(formulationRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/formulation/create-formulation")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Formulation Create Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link FormulationController#updateFormulation(FormulationRequest)}
	 */
	@Test
	void testUpdateFormulation() throws Exception {
		when(this.formulationService.updateFormulation((FormulationRequest) any())).thenReturn(1);

		FormulationRequest formulationRequest = new FormulationRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		formulationRequest.setInsertUser("Insert User");
		formulationRequest.setFormulationName("wwww");
		formulationRequest.setFormulationId(null);
		formulationRequest.setStatus("Product Name");
		formulationRequest.setDosageId(1);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		formulationRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		formulationRequest.setUpdateUser("ELN");
		String content = (new ObjectMapper()).writeValueAsString(formulationRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/formulation/update-formulation")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Formulation Update Successfully\"}"));
	}

	/**
	 * Method under test: {@link FormulationController#deleteFormulation(Integer)}
	 */
	@Test
	void testDeleteFormulation() throws Exception {
		when(this.formulationService.deleteFormulation((Integer) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/formulation/delete-formulation");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("formulationId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Formulation Delete Successfully\"}"));
	}

	/**
	 * Method under test: {@link FormulationController#deleteFormulation(Integer)}
	 */
	@Test
	void testDeleteFormulation2() throws Exception {
		when(this.formulationService.deleteFormulation((Integer) any())).thenReturn(1);
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/formulation/delete-formulation");
		deleteResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = deleteResult.param("formulationId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Formulation Delete Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link FormulationController#getExperimentsByUserId(Integer)}
	 */
	@Test
	void getExperimentsByUserId() throws Exception {
		when(this.experimentService.getExperimentsInfo((Integer) any())).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/formulation/get-experiments-by-user-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("dosageId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test:
	 * {@link FormulationController#getProjectsByDosageId(Integer)}
	 */
	@Test
	void getProjectsByDosageId() throws Exception {
		when(this.projectService.getProjects((Integer) any(), (Integer) any())).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/formulation/get-experiments-by-user-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("dosageId", "teamId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.formulationController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
}