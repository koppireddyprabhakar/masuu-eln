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

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamsRequest;

@Repository
@PropertySource(value = {"classpath:sql/teams-dao.properties"})
public class TeamsDaoImpl implements TeamsDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getTeamsById}")
	private String getTeamsByIdQuery;

	@Value(value="${getTeamsList}")
	private String getTeamsListQuery;

	@Value(value="${createTeams}")
	private String createTeamsQuery;

	@Value(value="${updateTeams}")
	private String updateTeamsQuery;

	@Value(value="${deleteTeams}")
	private String deleteTeamsQuery;
	
	@Value(value="${getFunctionalTeams}")
	private String getFunctionalTeamsQuery;
	
	@Override
	public TeamsDto getTeamsById(Integer teamsId) {
		List<TeamsDto> teamsList = jdbcTemplate.query(getTeamsByIdQuery + teamsId,
				new TeamsRowMapper());

		if(teamsList.isEmpty()) {
			return null;
		}

		return teamsList.get(0);
	}

	@Override
	public List<TeamsDto> getTeamsList() {
		return jdbcTemplate.query(getTeamsListQuery, new TeamsRowMapper());
	}

	@Override
	public Integer createTeams(TeamsRequest teamsRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamName", teamsRequest.getTeamName());
		parameters.addValue("deptId", teamsRequest.getDeptId());
		parameters.addValue("insertProcess", teamsRequest.getInsertProcess());
		parameters.addValue("insertDate", teamsRequest.getInsertDate());
		parameters.addValue("updateProcess", teamsRequest.getUpdateProcess());
		parameters.addValue("updateDate", teamsRequest.getUpdateDate());
		
		return namedParameterJdbcTemplate.update(createTeamsQuery, parameters);
	}

	@Override
	public Integer updateTeams(TeamsRequest teamsRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", teamsRequest.getTeamId());
		parameters.addValue("teamName", teamsRequest.getTeamName());
		parameters.addValue("deptId", teamsRequest.getDeptId());
		parameters.addValue("insertProcess", teamsRequest.getInsertProcess());
		parameters.addValue("insertDate", teamsRequest.getInsertDate());
		parameters.addValue("updateProcess", teamsRequest.getUpdateProcess());
		parameters.addValue("updateDate", teamsRequest.getUpdateDate());
		
		return namedParameterJdbcTemplate.update(updateTeamsQuery, parameters);
	}

	@Override
	public Integer deleteTeams(Integer teamsId) {
		return jdbcTemplate.update(deleteTeamsQuery, new Object[] {teamsId});
	}
	
	@Override
	public List<TeamsDto> getFormulationTeams() {
		return jdbcTemplate.query(getFunctionalTeamsQuery, new FunctionalTeamsMapper());
	}
	
	class TeamsRowMapper implements RowMapper<TeamsDto> {
		public TeamsDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			TeamsDto teams = new TeamsDto();
			teams.setTeamId(resultSet.getInt("TEAM_ID"));
			teams.setTeamName(resultSet.getString("TEAM_NAME"));
			teams.setDeptId(resultSet.getInt("DEPT_ID"));
			teams.setInsertDate(resultSet.getDate("INSERT_DATE"));
			teams.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			teams.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			teams.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));

			return  teams;
		};
	}
	
	class FunctionalTeamsMapper implements RowMapper<TeamsDto> {

		@Override
		public TeamsDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TeamsDto teams = new TeamsDto();
			teams.setTeamId(resultSet.getInt("TEAM_ID"));
			teams.setTeamName(resultSet.getString("TEAM_NAME"));
			teams.setDeptId(resultSet.getInt("DEPT_ID"));
			teams.setDosageId(resultSet.getInt("DOSAGE_ID"));
			
			return  teams;
		}
	}

}
