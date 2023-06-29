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

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;
import com.ectd.global.eln.services.DosageService;
import com.ectd.global.eln.services.ProjectService;
import com.ectd.global.eln.services.TeamsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { ProjectController.class })
@ExtendWith(SpringExtension.class)
class ProjectControllerTest {

	@Autowired
	private ProjectController projectController;

	@MockBean
	private ProjectService projectService;

	@MockBean
	private DosageService dosageService;

	@MockBean
	private TeamsService teamsService;

	/**
	 * Method under test: {@link ProjectController#getProjectById(Integer)}
	 */
	@Test
	void testGetProjectById() throws Exception {
		ProjectDto projectDto = new ProjectDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		projectDto.setInsertUser("Insert User");
		projectDto.setProjectId(6);
		projectDto.setProjectName("String");
		projectDto.setProductId(1);
		projectDto.setStatus("ACTIVE");
		projectDto.setStrength("500MG");
		projectDto.setDosageId(1);
		projectDto.setFormulationId(1);
		projectDto.setTeamId(5);
		projectDto.setMarketId(1);
		projectDto.setProjectName("Product Name");
		projectDto.setProjectId(7);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		projectDto.setUpdateUser("2020-03-01");
		when(this.projectService.getProjectById((Integer) any())).thenReturn(projectDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/project/get-project-by-id");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("projectId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE"
								+ "\",\"projectId\":7,\"projectName\":\"Product Name\",\"productId\":1,\"productName\":null,\"productCode\":null,\"strength\":\"500MG\",\"dosageId\":1,\"dosageName\":null,\"formulationId\":1,\"formulationName\":null,\"teamId\":5,\"teamName\":null,\"marketId\":1,\"markertName\":null}"));
	}

	/**
	 * Method under test: {@link ProjectController#getProjectById(Integer)}
	 */
	@Test
	void testGetProjectById2() throws Exception {
		ProjectDto projectDto = new ProjectDto();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		projectDto.setInsertUser("Insert User");
		projectDto.setProjectId(6);
		projectDto.setProjectName("String");
		projectDto.setProductId(1);
		projectDto.setStatus("ACTIVE");
		projectDto.setStrength("500MG");
		projectDto.setDosageId(1);
		projectDto.setFormulationId(1);
		projectDto.setTeamId(5);
		projectDto.setMarketId(1);
		projectDto.setProjectName("Product Name");
		projectDto.setProjectId(7);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		projectDto.setUpdateUser("2020-03-01");
		when(this.projectService.getProjectById((Integer) any())).thenReturn(projectDto);
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/project/get-project-by-id");
		getResult.contentType("application/json");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("projectId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"ACTIVE"
								+ "\",\"projectId\":7,\"projectName\":\"Product Name\",\"productId\":1,\"productName\":null,\"productCode\":null,\"strength\":\"500MG\",\"dosageId\":1,\"dosageName\":null,\"formulationId\":1,\"formulationName\":null,\"teamId\":5,\"teamName\":null,\"marketId\":1,\"markertName\":null}"));
	}

	/**
	 * Method under test: {@link ProjectController#getProjects()}
	 */
	@Test
	void getProjectsByDosageId() throws Exception {
		when(this.projectService.getProjects((Integer) any(), (Integer) any())).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/project/get-projects");
		MockHttpServletRequestBuilder requestBuilder = getResult.param("dosageId", "teamId", String.valueOf(1));
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link ProjectController#getProjects()}
	 */
	@Test
	void testGetProjects2() throws Exception {
		when(this.projectService.getProjects((Integer) any(), (Integer) any())).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/project/get-projects");
		getResult.contentType("application/json");

		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link ProjectController#createProject(ProjectRequest)}
	 */
	@Test
	void testCreateProject() throws Exception {
		when(this.projectService.createProject((ProjectRequest) any())).thenReturn(1);
		ProjectRequest projectRequest = new ProjectRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setInsertUser("Insert User");
		projectRequest.setProjectId(6);
		projectRequest.setProjectName("String");
		projectRequest.setProductId(1);
		projectRequest.setStatus("ACTIVE");
		projectRequest.setStrength("500MG");
		projectRequest.setDosageId(1);
		projectRequest.setFormulationId(1);
		projectRequest.setTeamId(5);
		projectRequest.setMarketId(1);
		projectRequest.setProjectName("Product Name");
		projectRequest.setProjectId(7);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(projectRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/project/create-project")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Project Create Successfully\"}"));

	}

	/**
	 * Method under test: {@link ProjectController#updateProject(ProjectRequest)}
	 */
	@Test
	void testUpdateProject() throws Exception {
		when(this.projectService.updateProject((ProjectRequest) any())).thenReturn(1);
		ProjectRequest projectRequest = new ProjectRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setInsertUser("Insert User");
		projectRequest.setProjectId(6);
		projectRequest.setProjectName("String");
		projectRequest.setProductId(1);
		projectRequest.setStatus("ACTIVE");
		projectRequest.setStrength("500MG");
		projectRequest.setDosageId(1);
		projectRequest.setFormulationId(1);
		projectRequest.setTeamId(5);
		projectRequest.setMarketId(1);
		projectRequest.setProjectName("Product Name");
		projectRequest.setProjectId(7);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(projectRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/project/update-project")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Project Update Successfully\"}"));

	}

	/**
	 * Method under test: {@link ProjectController#deleteProject(ProjectRequest)}
	 */
	@Test
	void testDeleteProject() throws Exception {
		when(this.projectService.updateProjectStatus((ProjectRequest) any())).thenReturn(1);
		ProjectRequest projectRequest = new ProjectRequest();
		LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setInsertUser("Insert User");
		projectRequest.setProjectId(6);
		projectRequest.setProjectName("String");
		projectRequest.setProductId(1);
		projectRequest.setStatus("ACTIVE");
		projectRequest.setStrength("500MG");
		projectRequest.setDosageId(1);
		projectRequest.setFormulationId(1);
		projectRequest.setTeamId(5);
		projectRequest.setMarketId(1);
		projectRequest.setProjectName("Product Name");
		projectRequest.setProjectId(7);
		LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
		projectRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
		projectRequest.setUpdateUser("2020-03-01");
		String content = (new ObjectMapper()).writeValueAsString(projectRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/project/delete-project")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Project Delete Successfully\"}"));

	}

	/**
	 * Method under test: {@link ProjectController#getDosagesAndFormulations()}
	 */
	@Test
	void testgetDosagesAndFormulations() throws Exception {
		when(this.dosageService.getDosagesAndFormulations()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/project/get-dosages-and-formulations");
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	/**
	 * Method under test: {@link ProjectController#getFormulationTeams()}
	 */
	@Test
	void testgetFormulationTeams() throws Exception {
		when(this.teamsService.getFormulationTeams()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/project/get-formulations-teams");
		MockMvcBuilders.standaloneSetup(this.projectController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
}