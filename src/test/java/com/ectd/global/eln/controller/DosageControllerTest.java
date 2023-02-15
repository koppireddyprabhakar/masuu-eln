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

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.request.DosageRequest;
import com.ectd.global.eln.services.DosageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { DosageController.class })
@ExtendWith(SpringExtension.class)
class DosageControllerTest {
	@Autowired
	private DosageController dosageController;

	@MockBean
	private DosageService dosageService;

	/**
	 * Method under test: {@link DosageController#getDosageById(Integer)}
	 */
	@Test
	void testGetDosageById() throws Exception {
		DosageDto dosageDto = new DosageDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageDto.setInsertUser("Insert User");
		dosageDto.setDosageId(123);
		dosageDto.setDosageName("Dosage Name");
		dosageDto.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageDto.setUpdateUser("2020-03-01");
		dosageDto.setFormulations(null);
		when(this.dosageService.getDosageById((Integer) any())).thenReturn(dosageDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/dosage/get-dosage-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("dosageId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
								+ "\",\"dosageId\":123,\"dosageName\":\"Dosage Name\",\"formulations\":null}"));

	}

	/**
	 * Method under test: {@link DosageController#getDosageById(Integer)}
	 */
	@Test
	void testGetDosageById2() throws Exception {
		DosageDto dosageDto = new DosageDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageDto.setInsertUser("Insert User");
		dosageDto.setDosageId(123);
		dosageDto.setDosageName("Dosage Name");
		dosageDto.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageDto.setUpdateUser("2020-03-01");
		dosageDto.setFormulations(null);
		when(this.dosageService.getDosageById((Integer) any())).thenReturn(dosageDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/dosage/get-dosage-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("dosageId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
								+ "\",\"dosageId\":123,\"dosageName\":\"Dosage Name\",\"formulations\":null}"));

	}

	/**
	 * Method under test: {@link DosageController#getDosages()}
	 */
	@Test
	void testGetDosages() throws Exception {
		when(this.dosageService.getDosages()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/dosage/get-dosages");
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link DosageController#getDosages()}
	 */
	@Test
	void testGetDosages2() throws Exception {
		when(this.dosageService.getDosages()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/dosage/get-dosages");
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link DosageController#createDosage(DosageRequest)}
	 */
	@Test
	void testCreateDosage() throws Exception {
		when(this.dosageService.createDosage((DosageRequest) any())).thenReturn(1);

		DosageRequest dosageRequest = new DosageRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setInsertUser("Insert User");
		dosageRequest.setDosageId(123);
		dosageRequest.setDosageName("Dosage Name");
		dosageRequest.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setUpdateUser("2020-03-01");
		dosageRequest.setFormulations(null);
		String content = (new ObjectMapper()).writeValueAsString(dosageRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dosage/create-dosage")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Dosage Create Successfully\"}"));
	}

	/**
	 * Method under test: {@link DosageController#updateDosage(DosageRequest)}
	 */
	@Test
	void testUpdateDosage() throws Exception {
		when(this.dosageService.updateDosage((DosageRequest) any())).thenReturn(1);

		DosageRequest dosageRequest = new DosageRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setInsertUser("Insert User");
		dosageRequest.setDosageId(123);
		dosageRequest.setDosageName("Dosage Name");
		dosageRequest.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setUpdateUser("2020-03-01");
		dosageRequest.setFormulations(null);
		String content = (new ObjectMapper()).writeValueAsString(dosageRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/dosage/update-dosage")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Dosage Update Successfully\"}"));
	}

	/**
	 * Method under test: {@link DosageController#updateDosage(DosageRequest)}
	 */
	@Test
	void testdeleteDosage() throws Exception {
		when(this.dosageService.deleteDosage((DosageRequest) any())).thenReturn(true);

		DosageRequest dosageRequest = new DosageRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setInsertUser("Insert User");
		dosageRequest.setDosageId(123);
		dosageRequest.setDosageName("Dosage Name");
		dosageRequest.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setUpdateUser("2020-03-01");
		dosageRequest.setFormulations(null);
		String content = (new ObjectMapper()).writeValueAsString(dosageRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/dosage/delete-dosage")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Dosage Delete Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link DosageController#saveDosageWithFormulations(Boolean)}
	 */
	@Test
	void testsaveDosageWithFormulations() throws Exception {
		when(this.dosageService.saveDosageWithFormulations((DosageRequest) any())).thenReturn(true);

		DosageRequest dosageRequest = new DosageRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setInsertUser("Insert User");
		dosageRequest.setDosageId(123);
		dosageRequest.setDosageName("Dosage Name");
		dosageRequest.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setUpdateUser("2020-03-01");
		dosageRequest.setFormulations(null);
		String content = (new ObjectMapper()).writeValueAsString(dosageRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dosage/save-dosage-formulations")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Dosage Saved Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link DosageController#updateDosageWithFormulations(DosageRequest)}
	 */
	@Test
	void testupdateDosageWithFormulations() throws Exception {
		when(this.dosageService.updateDosageWithFormulations((DosageRequest) any())).thenReturn(true);

		DosageRequest dosageRequest = new DosageRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setInsertUser("Insert User");
		dosageRequest.setDosageId(123);
		dosageRequest.setDosageName("Dosage Name");
		dosageRequest.setStatus("Status");
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		dosageRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		dosageRequest.setUpdateUser("2020-03-01");
		dosageRequest.setFormulations(null);
		String content = (new ObjectMapper()).writeValueAsString(dosageRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/dosage/update-dosage-formulations")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.dosageController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Dosage Updated Successfully\"}"));
	}
}
