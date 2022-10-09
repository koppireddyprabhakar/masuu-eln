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

import com.ectd.global.eln.dto.ProjectTeamDto;
import com.ectd.global.eln.request.ProjectTeamRequest;

@Repository
@PropertySource(value = {"classpath:sql/project-dao.properties"})
public class ProjectTeamDaoImpl implements ProjectTeamDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getProjectTeamById}")
	private String getProjectTeamByIdQuery;

	@Value(value="${getProjectTeamList}")
	private String getProjectTeamListQuery;

	@Value(value="${createProjectTeam}")
	private String createProjectTeamQuery;

	@Value(value="${updateProjectTeam}")
	private String updateProjectTeamQuery;

	@Value(value="${deleteProjectTeam}")
	private String deleteProjectTeamQuery;

	@Override
	public ProjectTeamDto getProjectTeamById(Integer projectTeamId) {
		List<ProjectTeamDto> projectTeams = jdbcTemplate.query(getProjectTeamByIdQuery + projectTeamId,
				new ProjectTeamRowMapper());

		if(projectTeams.isEmpty()) {
			return null;
		}

		return projectTeams.get(0);
	}

	@Override
	public List<ProjectTeamDto> getProjectTeams() {
		return jdbcTemplate.query(getProjectTeamListQuery, new ProjectTeamRowMapper());
	}

	@Override
	public Integer createProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("insertDate", projectTeamRequest.getInsertDate());
		parameters.addValue("insertProcess", projectTeamRequest.getInsertProcess());
		parameters.addValue("updateDate", projectTeamRequest.getUpdateDate());
		parameters.addValue("updateProcess", projectTeamRequest.getUpdateProcess());

		return namedParameterJdbcTemplate.update(createProjectTeamQuery, parameters);
	}

	@Override
	public Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectTeamId", projectTeamRequest.getProjectTeamId());
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("insertDate", projectTeamRequest.getInsertDate());
		parameters.addValue("insertProcess", projectTeamRequest.getInsertProcess());
		parameters.addValue("updateDate", projectTeamRequest.getUpdateDate());
		parameters.addValue("updateProcess", projectTeamRequest.getUpdateProcess());

		return namedParameterJdbcTemplate.update(updateProjectTeamQuery, parameters);
	}

	@Override
	public Integer deleteProjectTeam(Integer projectTeamId) {
		return jdbcTemplate.update(deleteProjectTeamQuery, new Object[] {projectTeamId});
	}

	class ProjectTeamRowMapper implements RowMapper<ProjectTeamDto> {
		public ProjectTeamDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			ProjectTeamDto projectTeamDto = new ProjectTeamDto();
			projectTeamDto.setProjectTeamId(resultSet.getInt("PROJECT_TEAM_ID"));
			projectTeamDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			projectTeamDto.setTeamId(resultSet.getInt("TEAM_ID"));
			projectTeamDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			projectTeamDto.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));
			projectTeamDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			projectTeamDto.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			
			return  projectTeamDto;
		};
	}

}
