package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;
import com.ectd.global.eln.request.ProjectTeamRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/project-dao.properties"})
public class ProjectDaoImpl implements ProjectDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.project.by.id}")
	private String GET_PROJECT_BY_ID_QUERY;

	@Value(value="${get.project.list}")
	private String GET_PROJECT_LIST_QUERY;

	@Value(value="${create.project}")
	private String CREATE_PROJECT_QUERY;

	@Value(value="${update.project}")
	private String UPDATE_PROJECT_QUERY;

	@Value(value="${delete.project}")
	private String DELETE_PROJECT_QUERY;
	
	@Value(value="${create.project.team}")
	private String CREATE_PROJECT_TEAM_QUERY;
	
	@Value(value="${update.project.team}")
	private String UPDATE_PROJECT_TEAM_QUERY;
	
	@Value("${inactivate.project}")
	private String INACTIVATE_PROJECT_QUERY;
	
	@Value("${inactivate.project.team}")
	private String INACTIVATE_PROJECT_TEAM_QUERY;

	@Override
	public ProjectDto getProjectById(Integer projectId) {
		List<ProjectDto> projects = jdbcTemplate.query(GET_PROJECT_BY_ID_QUERY + projectId,
				new ProjectRowMapper());

		if(projects.isEmpty()) {
			return null;
		}

		return projects.get(0);
	}

	@Override
	public List<ProjectDto> getProjects(Integer dosageId, Integer teamId) {

		StringBuilder sb = new StringBuilder(GET_PROJECT_LIST_QUERY);

		if(dosageId != null) {
			sb.append(" AND P.DOSAGE_ID = ").append(dosageId);
		}
		
		if(teamId != null) {
			sb.append(" AND P.TEAM_ID = ").append(teamId);
		}
		
		sb.append(" ORDER BY P.INSERT_DATE DESC");

		return jdbcTemplate.query(sb.toString(), new ProjectRowMapper());
	}

	@Override
	public Integer createProject(ProjectRequest projectRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		parameters.addValue("projectName", projectRequest.getProjectName());
		parameters.addValue("productId", projectRequest.getProductId());
		parameters.addValue("productName", projectRequest.getProductName());
		parameters.addValue("productCode", projectRequest.getProductCode());
		parameters.addValue("status", ProjectRequest.PROJECT_STATUS.NEW.name());
		parameters.addValue("strength", projectRequest.getStrength());
		parameters.addValue("dosageId", projectRequest.getDosageId());
		parameters.addValue("dosageName", projectRequest.getDosageName());
		parameters.addValue("formulationId", projectRequest.getFormulationId());
		parameters.addValue("formulationName", projectRequest.getFormulationName());
		parameters.addValue("teamId", projectRequest.getTeamId());
		parameters.addValue("teamName", projectRequest.getTeamName());
		parameters.addValue("marketId", projectRequest.getMarketId());
		parameters.addValue("markertName", projectRequest.getMarkertName());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);

		namedParameterJdbcTemplate.update(CREATE_PROJECT_QUERY, parameters, keyHolder);
		
		Integer projectId = keyHolder.getKey().intValue();
		
		projectRequest.getProjectTeam().setProjectId(projectId);
		this.createProjectTeam(projectRequest.getProjectTeam());
		
		return projectId;
	}

	@Override
	public Integer updateProject(ProjectRequest projectRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectRequest.getProjectId());
		parameters.addValue("projectName", projectRequest.getProjectName());
		parameters.addValue("productId", projectRequest.getProductId());
		parameters.addValue("productName", projectRequest.getProductName());
		parameters.addValue("productCode", projectRequest.getProductCode());
		parameters.addValue("status", projectRequest.getStatus());
		parameters.addValue("strength", projectRequest.getStrength());
		parameters.addValue("dosageId", projectRequest.getDosageId());
		parameters.addValue("dosageName", projectRequest.getDosageName());
		parameters.addValue("formulationId", projectRequest.getFormulationId());
		parameters.addValue("formulationName", projectRequest.getFormulationName());
		parameters.addValue("teamId", projectRequest.getTeamId());
		parameters.addValue("teamName", projectRequest.getTeamName());
		parameters.addValue("marketId", projectRequest.getMarketId());
		parameters.addValue("markertName", projectRequest.getMarkertName());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);

		namedParameterJdbcTemplate.update(UPDATE_PROJECT_QUERY, parameters);
		
		return this.updateProjectTeam(projectRequest.getProjectTeam());
	}
	
	private Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId",  projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);

		return namedParameterJdbcTemplate.update(UPDATE_PROJECT_TEAM_QUERY, parameters);
	}
	
	@Override
	public Integer inActivateProject(ProjectRequest projectRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectRequest.getProjectId());
		parameters.addValue("status", ElnUtils.STATUS.INACTIVE.getValue());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		
		namedParameterJdbcTemplate.update(INACTIVATE_PROJECT_QUERY, parameters);
		
		return this.inActivateProjectTeam(projectRequest.getProjectTeam());
	}
	
	private Integer inActivateProjectTeam(ProjectTeamRequest projectTeamRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("status", ElnUtils.STATUS.INACTIVE.getValue());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		
		return namedParameterJdbcTemplate.update(INACTIVATE_PROJECT_TEAM_QUERY, parameters);
	}

	@Override
	public Integer deleteProject(Integer projectId) {
		return jdbcTemplate.update(DELETE_PROJECT_QUERY, new Object[] {projectId});
	}
	
//	@Override
	private Integer createProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);

		return namedParameterJdbcTemplate.update(CREATE_PROJECT_TEAM_QUERY, parameters);
	}

	class ProjectRowMapper implements RowMapper<ProjectDto> {
		public ProjectDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			ProjectDto projectDto = new ProjectDto();
			projectDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			projectDto.setProjectName(resultSet.getString("PROJECT_NAME"));
			projectDto.setProductId(resultSet.getInt("PRODUCT_ID"));
			projectDto.setProductName(resultSet.getString("PRODUCT_NAME"));
			projectDto.setProductCode(resultSet.getString("PRODUCT_CODE"));
			projectDto.setStatus(resultSet.getString("STATUS"));
			projectDto.setStrength(resultSet.getString("STRENGTH"));
			projectDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			projectDto.setDosageName(resultSet.getString("DOSAGE_NAME"));
			projectDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
			projectDto.setFormulationName(resultSet.getString("FORMULATION_NAME"));
			projectDto.setTeamId(resultSet.getInt("TEAM_ID"));
			projectDto.setTeamName(resultSet.getString("TEAM_NAME"));
			projectDto.setMarketId(resultSet.getInt("MARKET_ID"));
			projectDto.setMarkertName(resultSet.getString("MARKET_NAME"));
			projectDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			projectDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			projectDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			projectDto.setInsertUser(resultSet.getString("INSERT_USER"));

			ProjectTeamRequest projectTeamRequest = new ProjectTeamRequest();
			projectTeamRequest.setProjectTeamId(resultSet.getInt("PROJECT_TEAM_ID"));
			projectTeamRequest.setProjectId(resultSet.getInt("PROJECTID"));
			projectTeamRequest.setTeamId(resultSet.getInt("TEAMID"));
			projectDto.setProjectTeam(projectTeamRequest);
			
			return  projectDto;
		};
	}


}
