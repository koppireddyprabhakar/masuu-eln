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
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/project-dao.properties"})
public class ProjectTeamDaoImpl implements ProjectTeamDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.project.team.by.id}")
	private String GET_PROJECT_TEAM_BY_ID;

	@Value(value="${get.project.team.list}")
	private String GET_PROJECT_TEAM_LIST;

	@Value(value="${create.project.team}")
	private String CREATE_PROJECT_TEAM_QUERY;

	@Value(value="${update.project.team}")
	private String UPDATE_PROJECT_TEAM_QUERY;

	@Value(value="${delete.project.team}")
	private String DELETE_PROJECT_TEAM_QUERY;

	@Override
	public ProjectTeamDto getProjectTeamById(Integer projectTeamId) {
		List<ProjectTeamDto> projectTeams = jdbcTemplate.query(GET_PROJECT_TEAM_BY_ID + projectTeamId,
				new ProjectTeamRowMapper());

		if(projectTeams.isEmpty()) {
			return null;
		}

		return projectTeams.get(0);
	}

	@Override
	public List<ProjectTeamDto> getProjectTeams() {
		return jdbcTemplate.query(GET_PROJECT_TEAM_LIST, new ProjectTeamRowMapper());
	}

	@Override
	public Integer createProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", projectTeamRequest.getInsertUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", projectTeamRequest.getUpdateUser());

		return namedParameterJdbcTemplate.update(CREATE_PROJECT_TEAM_QUERY, parameters);
	}

	@Override
	public Integer updateProjectTeam(ProjectTeamRequest projectTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectTeamId", projectTeamRequest.getProjectTeamId());
		parameters.addValue("projectId", projectTeamRequest.getProjectId());
		parameters.addValue("teamId", projectTeamRequest.getTeamId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", projectTeamRequest.getUpdateUser());

		return namedParameterJdbcTemplate.update(UPDATE_PROJECT_TEAM_QUERY, parameters);
	}

	@Override
	public Integer deleteProjectTeam(Integer projectTeamId) {
		return jdbcTemplate.update(DELETE_PROJECT_TEAM_QUERY, new Object[] {projectTeamId});
	}

	class ProjectTeamRowMapper implements RowMapper<ProjectTeamDto> {
		public ProjectTeamDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			ProjectTeamDto projectTeamDto = new ProjectTeamDto();
			projectTeamDto.setProjectTeamId(resultSet.getInt("PROJECT_TEAM_ID"));
			projectTeamDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			projectTeamDto.setTeamId(resultSet.getInt("TEAM_ID"));
			projectTeamDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			projectTeamDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			projectTeamDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			projectTeamDto.setInsertUser(resultSet.getString("INSERT_USER"));
			
			return  projectTeamDto;
		};
	}

}
