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
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ProjectRequest;
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

	@Value(value="${getProjectById}")
	private String getProjectByIdQuery;

	@Value(value="${getProjectList}")
	private String getProjectListQuery;

	@Value(value="${createProject}")
	private String createProjectQuery;

	@Value(value="${updateProject}")
	private String updateProjectQuery;

	@Value(value="${deleteProject}")
	private String deleteProjectQuery;

	@Override
	public ProjectDto getProjectById(Integer projectId) {
		List<ProjectDto> projects = jdbcTemplate.query(getProjectByIdQuery + projectId,
				new ProjectRowMapper());

		if(projects.isEmpty()) {
			return null;
		}

		return projects.get(0);
	}

	@Override
	public List<ProjectDto> getProjects() {
		return jdbcTemplate.query(getProjectListQuery, new ProjectRowMapper());
	}

	@Override
	public Integer createProject(ProjectRequest projectRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectName", projectRequest.getProjectName());
		parameters.addValue("productId", projectRequest.getProductId());
		parameters.addValue("status", ProjectRequest.PROJECT_STATUS.NEW);
		parameters.addValue("strength", projectRequest.getStrength());
		parameters.addValue("dosageId", projectRequest.getDosageId());
		parameters.addValue("formulationId", projectRequest.getFormulationId());
		parameters.addValue("teamId", projectRequest.getTeamId());
		parameters.addValue("marketId", projectRequest.getMarketId());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertProcess", projectRequest.getInsertProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", projectRequest.getUpdateProcess());

		return namedParameterJdbcTemplate.update(createProjectQuery, parameters);
	}

	@Override
	public Integer updateProject(ProjectRequest projectRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectRequest.getProjectId());
		parameters.addValue("projectName", projectRequest.getProjectName());
		parameters.addValue("productId", projectRequest.getProductId());
		parameters.addValue("status", projectRequest.getStatus());
		parameters.addValue("strength", projectRequest.getStrength());
		parameters.addValue("dosageId", projectRequest.getDosageId());
		parameters.addValue("formulationId", projectRequest.getFormulationId());
		parameters.addValue("teamId", projectRequest.getTeamId());
		parameters.addValue("marketId", projectRequest.getMarketId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", projectRequest.getUpdateProcess());

		return namedParameterJdbcTemplate.update(updateProjectQuery, parameters);
	}

	@Override
	public Integer deleteProject(Integer projectId) {
		return jdbcTemplate.update(deleteProjectQuery, new Object[] {projectId});
	}

	class ProjectRowMapper implements RowMapper<ProjectDto> {
		public ProjectDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			ProjectDto projectDto = new ProjectDto();
			projectDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			projectDto.setProjectName(resultSet.getString("PROJECT_NAME"));
			projectDto.setProductId(resultSet.getInt("PRODUCT_ID"));
			projectDto.setStatus(resultSet.getString("STATUS"));
			projectDto.setStrength(resultSet.getString("STRENGTH"));
			projectDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			projectDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
			projectDto.setTeamId(resultSet.getInt("TEAM_ID"));
			projectDto.setMarketId(resultSet.getInt("MARKET_ID"));
			projectDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			projectDto.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));
			projectDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			projectDto.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			
			return  projectDto;
		};
	}
	

}
