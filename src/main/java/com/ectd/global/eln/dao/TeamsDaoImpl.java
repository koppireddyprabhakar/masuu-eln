package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
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
import org.springframework.util.CollectionUtils;

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

	@Value(value="${create.team.dosage}")
	private String createTeamDosageQuery;

	@Value(value="${update.team.dosage}")
	private String updateTeamDosageQuery;

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
		parameters.addValue("insertProcess", teamsRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", teamsRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(createTeamsQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	private int[] batchInsert(List<TeamDosage> teamDosages, Integer teamId) {

		teamDosages.forEach(td -> 
		{	
			td.setTeamId(teamId);
			td.setInsertProcess("ELN");
			td.setUpdateProcess("ELN");
			td.setInsertDate(ElnUtils.getTimeStamp());
			td.setUpdateDate(ElnUtils.getTimeStamp());
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(teamDosages.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( createTeamDosageQuery, batch);
	}

	@Override
	public Integer updateTeams(TeamsRequest teamsRequest) {
		this.update(teamsRequest);

		List<TeamDosage> updateTeamDosages = teamsRequest.getTeamDosages().stream().filter(td -> !(ObjectUtils.isEmpty(td.getTeamId())) ).collect(Collectors.toList());
		List<TeamDosage> insertTeamDosages = teamsRequest.getTeamDosages().stream().filter(td -> ObjectUtils.isEmpty(td.getTeamId())).collect(Collectors.toList());

		int[] updatedRows = null;

		if(!CollectionUtils.isEmpty(updateTeamDosages)) {
			updatedRows = this.batchUpdate(updateTeamDosages);
		}

		if(!CollectionUtils.isEmpty(insertTeamDosages)) {
			this.batchInsert(insertTeamDosages, teamsRequest.getTeamId());
		}

		if(updatedRows != null && updatedRows.length > 0) {
			return updatedRows.length;
		}

		return 0;
	}

	private Integer update(TeamsRequest teamsRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", teamsRequest.getTeamId());
		parameters.addValue("teamName", teamsRequest.getTeamName());
		parameters.addValue("deptId", teamsRequest.getDeptId());
		parameters.addValue("updateProcess", teamsRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateTeamsQuery, parameters);
	}

	private int[] batchUpdate(List<TeamDosage> teamDosages) {

		teamDosages.forEach(td -> {
			td.setUpdateProcess("ELN");
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
