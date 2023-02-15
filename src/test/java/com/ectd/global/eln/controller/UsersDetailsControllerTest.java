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

import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UsersDetailsRequest;
import com.ectd.global.eln.services.UsersDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { UsersDetailsController.class })
@ExtendWith(SpringExtension.class)
class UsersDetailsControllerTest {
	@Autowired
	private UsersDetailsController usersDetailsController;

	@MockBean
	private UsersDetailsService usersDetailsService;

	/**
	 * Method under test:
	 * {@link UsersDetailsController#getUsersDetailsById(Integer)}
	 */
	@Test
	void testgetUsersDetailsById() throws Exception {
		UsersDetailsDto usersDetailsDto = new UsersDetailsDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsDto.setInsertUser("Insert User");
		usersDetailsDto.setAddressLine1("D.no 62-4/41");
		usersDetailsDto.setAddressLine2("Jawaharnagar Road , Sriharipuram");
		usersDetailsDto.setCity("HYD");
		usersDetailsDto.setContactNo(934671352);
		usersDetailsDto.setDateOfBirth(null);
		usersDetailsDto.setDepartmentName(null);
		usersDetailsDto.setDeptId(null);
		usersDetailsDto.setDosageId(null);
		usersDetailsDto.setFirstName("uday");
		usersDetailsDto.setGender("Male");
		usersDetailsDto.setLastName("kumar");
		usersDetailsDto.setMailId("asa@gmail.com");
		usersDetailsDto.setRoleId(3);
		usersDetailsDto.setRoleName(null);
		usersDetailsDto.setStatus(null);
		usersDetailsDto.setTeamId(null);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsDto.setUpdateUser("2020-03-01");
		usersDetailsDto.setUserId(null);
		usersDetailsDto.setZipCode("530011");
		when(this.usersDetailsService.getUsersDetailsById((Integer) any())).thenReturn(usersDetailsDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/usersDetails/get-users-details-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("userId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":null,\"userId\":null,\"firstName\":\"uday\",\"lastName\":\"kumar\",\"dateOfBirth\":null,\"gender\":\"Male\",\"deptId\":null,\"roleId\":3,\"contactNo\":934671352,\"mailId\":\"asa@gmail.com\",\"addressLine1\":\"D.no 62-4/41\",\"addressLine2\":\"Jawaharnagar Road , Sriharipuram\",\"city\":\"HYD\",\"zipCode\":\"530011\",\"dosageId\":null,\"roleName\":null,\"departmentName\":null,\"teamId\":null}"));
	}

	/**
	 * Method under test:
	 * {@link UsersDetailsController#getUsersDetailsById(Integer)}
	 */
	@Test
	void testgetUsersDetailsById2() throws Exception {
		UsersDetailsDto usersDetailsDto = new UsersDetailsDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsDto.setInsertUser("Insert User");
		usersDetailsDto.setAddressLine1("D.no 62-4/41");
		usersDetailsDto.setAddressLine2("Jawaharnagar Road , Sriharipuram");
		usersDetailsDto.setCity("HYD");
		usersDetailsDto.setContactNo(934671352);
		usersDetailsDto.setDateOfBirth(null);
		usersDetailsDto.setDepartmentName(null);
		usersDetailsDto.setDeptId(null);
		usersDetailsDto.setDosageId(null);
		usersDetailsDto.setFirstName("uday");
		usersDetailsDto.setGender("Male");
		usersDetailsDto.setLastName("kumar");
		usersDetailsDto.setMailId("asa@gmail.com");
		usersDetailsDto.setRoleId(3);
		usersDetailsDto.setRoleName(null);
		usersDetailsDto.setStatus(null);
		usersDetailsDto.setTeamId(null);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsDto.setUpdateUser("2020-03-01");
		usersDetailsDto.setUserId(null);
		usersDetailsDto.setZipCode("530011");
		when(this.usersDetailsService.getUsersDetailsById((Integer) any())).thenReturn(usersDetailsDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/usersDetails/get-users-details-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("userId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":null,\"userId\":null,\"firstName\":\"uday\",\"lastName\":\"kumar\",\"dateOfBirth\":null,\"gender\":\"Male\",\"deptId\":null,\"roleId\":3,\"contactNo\":934671352,\"mailId\":\"asa@gmail.com\",\"addressLine1\":\"D.no 62-4/41\",\"addressLine2\":\"Jawaharnagar Road , Sriharipuram\",\"city\":\"HYD\",\"zipCode\":\"530011\",\"dosageId\":null,\"roleName\":null,\"departmentName\":null,\"teamId\":null}"));
	}

	/**
	 * Method under test: {@link UsersDetailsController#getUsersDetails()}
	 */
	@Test
	void testGetUsersDetails() throws Exception {
		when(this.usersDetailsService.getUsersDetails(null, null)).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/usersDetails/get-users-details");
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link UsersDetailsController#getUsersDetails()}
	 */
	@Test
	void testGetUsersDetails2() throws Exception {
		when(this.usersDetailsService.getUsersDetails(null, null)).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/usersDetails/get-users-details");
		getResult.contentType("application/json");
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test:
	 * {@link UsersDetailsController#createUsersDetails(usersDetailsRequest)}
	 */
	@Test
	void testCreateUsersDetails() throws Exception {
		when(this.usersDetailsService.createUsersDetails((UsersDetailsRequest) any())).thenReturn(true);

		UsersDetailsRequest usersDetailsRequest = new UsersDetailsRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setInsertUser("Insert User");
		usersDetailsRequest.setAddressLine1("D.no 62-4/41");
		usersDetailsRequest.setAddressLine2("Jawaharnagar Road , Sriharipuram");
		usersDetailsRequest.setCity("HYD");
		usersDetailsRequest.setContactNo(null);
		usersDetailsRequest.setDateOfBirth(null);
		usersDetailsRequest.setDeptId(null);
		usersDetailsRequest.setFirstName("uday");
		usersDetailsRequest.setGender("Male");
		usersDetailsRequest.setLastName("kumar");
		usersDetailsRequest.setMailId("asa@gmail.com");
		usersDetailsRequest.setRoleId(3);
		usersDetailsRequest.setStatus(null);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setUpdateUser("2020-03-01");
		usersDetailsRequest.setUserId(null);
		usersDetailsRequest.setZipCode("530011");
		String content = (new ObjectMapper()).writeValueAsString(usersDetailsRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/usersDetails/create-users-details")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Users Details Create Successfully\"}"));
	}

	/**
	 * Method under test:
	 * {@link UsersDetailsController#createUsersDetails(usersDetailsRequest)}
	 */
	@Test
	void testUpdateteUsersDetails() throws Exception {
		when(this.usersDetailsService.updateUsersDetails((UsersDetailsRequest) any())).thenReturn(1);
		UsersDetailsRequest usersDetailsRequest = new UsersDetailsRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setInsertUser("Insert User");
		usersDetailsRequest.setAddressLine1("D.no 62-4/41");
		usersDetailsRequest.setAddressLine2("Jawaharnagar Road , Sriharipuram");
		usersDetailsRequest.setCity("HYD");
		usersDetailsRequest.setContactNo(null);
		usersDetailsRequest.setDateOfBirth(null);
		usersDetailsRequest.setDeptId(null);
		usersDetailsRequest.setFirstName("uday");
		usersDetailsRequest.setGender("Male");
		usersDetailsRequest.setLastName("kumar");
		usersDetailsRequest.setMailId("asa@gmail.com");
		usersDetailsRequest.setRoleId(3);
		usersDetailsRequest.setStatus(null);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setUpdateUser("2020-03-01");
		usersDetailsRequest.setUserId(null);
		usersDetailsRequest.setZipCode("530011");
		String content = (new ObjectMapper()).writeValueAsString(usersDetailsRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/usersDetails/update-users-details")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Users Details Update Successfully\"}"));
	}

	/**
	 * Method under test: {@link UsersDetailsController#deleteUsersDetails(Integer)}
	 */
	@Test
	void testDeleteteUsersDetails() throws Exception {
		when(this.usersDetailsService.deleteUsersDetails((UsersDetailsRequest) any())).thenReturn(1);
		UsersDetailsRequest usersDetailsRequest = new UsersDetailsRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setInsertUser("Insert User");
		usersDetailsRequest.setAddressLine1("D.no 62-4/41");
		usersDetailsRequest.setAddressLine2("Jawaharnagar Road , Sriharipuram");
		usersDetailsRequest.setCity("HYD");
		usersDetailsRequest.setContactNo(null);
		usersDetailsRequest.setDateOfBirth(null);
		usersDetailsRequest.setDeptId(null);
		usersDetailsRequest.setFirstName("uday");
		usersDetailsRequest.setGender("Male");
		usersDetailsRequest.setLastName("kumar");
		usersDetailsRequest.setMailId("asa@gmail.com");
		usersDetailsRequest.setRoleId(3);
		usersDetailsRequest.setStatus(null);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		usersDetailsRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		usersDetailsRequest.setUpdateUser("2020-03-01");
		usersDetailsRequest.setUserId(null);
		usersDetailsRequest.setZipCode("530011");
		String content = (new ObjectMapper()).writeValueAsString(usersDetailsRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/usersDetails/delete-users-details").contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.usersDetailsController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Users Details Delete Successfully\"}"));
	}
}
