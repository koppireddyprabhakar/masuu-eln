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
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.TeamDosage;
import com.ectd.global.eln.request.TeamsRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/teams-dao.properties"})
public class TeamsDaoImpl implements TeamsDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.teams.by.id}")
	private String GET_TEAMS_BY_ID_QUERY;

	@Value(value="${get.teams.list}")
	private String GET_TEAMS_LIST_QUERY;

	@Value(value="${createTeams}")
	private String createTeamsQuery;

	@Value(value="${updateTeams}")
	private String updateTeamsQuery;

	@Value(value="${deleteTeams}")
	private String deleteTeamsQuery;

	@Value(value="${getFunctionalTeams}")
	private String getFunctionalTeamsQuery;

	@Value(value="${create.team.dosage}")
	private String createTeamDosageQuery;

	@Value(value="${update.team.dosage}")
	private String updateTeamDosageQuery;

	@Override
	public TeamsDto getTeamsById(Integer teamsId) {
		List<TeamsDto> teamsList = jdbcTemplate.query(GET_TEAMS_BY_ID_QUERY + teamsId,
				new TeamsRowMapper());

		if(teamsList.isEmpty()) {
			return null;
		}

		return teamsList.get(0);
	}

	@Override
	public List<TeamsDto> getTeamsList() {
		return jdbcTemplate.query(GET_TEAMS_LIST_QUERY, new TeamsRowMapper());
	}

	@Override
	public Boolean createTeams(TeamsRequest teamsRequest) {

		Integer teamId = this.create(teamsRequest);

		int[] insertedRows = this.batchInsert(teamsRequest.getTeamDosages(), teamId);

		if(insertedRows.length > 0) {
			return true;
		}

		return false;
	}

	private Integer create(TeamsRequest teamsRequest) {
		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamName", teamsRequest.getTeamName());
		parameters.addValue("deptId", teamsRequest.getDeptId());
		parameters.addValue("status", teamsRequest.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(createTeamsQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public int[] batchInsert(List<TeamDosage> teamDosages, Integer teamId) {

		teamDosages.forEach(td -> 
		{	
			td.setTeamId(teamId);
			td.setInsertUser("ELN");
			td.setUpdateUser("ELN");
			td.setInsertDate(ElnUtils.getTimeStamp());
			td.setUpdateDate(ElnUtils.getTimeStamp());
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(teamDosages.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( createTeamDosageQuery, batch);
	}

	@Override
	public Integer updateTeams(TeamsRequest teamsRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", teamsRequest.getTeamId());
		parameters.addValue("teamName", teamsRequest.getTeamName());
		parameters.addValue("deptId", teamsRequest.getDeptId());
		parameters.addValue("status", teamsRequest.getStatus());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateTeamsQuery, parameters);
	}

	public int[] batchUpdate(List<TeamDosage> teamDosages) {

		teamDosages.forEach(td -> {
			td.setUpdateUser("ELN");
			td.setUpdateDate(ElnUtils.getTimeStamp());
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(teamDosages.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( updateTeamDosageQuery, batch);
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
			teams.setStatus(resultSet.getString("STATUS"));
			teams.setDosageId(resultSet.getInt("DOSAGE_ID"));
			teams.setDosageName(resultSet.getString("DOSAGE_NAME"));
			teams.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
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
