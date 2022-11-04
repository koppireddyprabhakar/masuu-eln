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

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/experiment-dao.properties"})
public class ExperimentDaoImpl implements ExperimentDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getExperimentById}")
	private String getExperimentByIdQuery;

	@Value(value="${getExperimentList}")
	private String getExperimentListQuery;

	@Value(value="${createExperiment}")
	private String createExperimentQuery;

	@Value(value="${updateExperiment}")
	private String updateExperimentQuery;

	@Value(value="${deleteExperiment}")
	private String deleteExperimentQuery;

	@Override
	public ExperimentDto getExperimentById(Integer experimentId) {
		List<ExperimentDto> experiments = jdbcTemplate.query(getExperimentByIdQuery + experimentId,
				new ExperimentRowMapper());

		if(experiments.isEmpty()) {
			return null;
		}

		return experiments.get(0);
	}

	@Override
	public List<ExperimentDto> getExperiments() {
		return jdbcTemplate.query(getExperimentListQuery, new ExperimentRowMapper());
	}

	@Override
	public Integer createExperiment(ExperimentRequest experimentRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("projectId", experimentRequest.getProjectId());
		parameters.addValue("teamId", experimentRequest.getTeamId());
		parameters.addValue("userId", experimentRequest.getUserId());
		parameters.addValue("experimentName", experimentRequest.getExperimentName());
		parameters.addValue("experimentStatus", experimentRequest.getExperimentStatus());
		parameters.addValue("summary", experimentRequest.getSummary());
		parameters.addValue("status", experimentRequest.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(createExperimentQuery, parameters);
	}

	@Override
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("expId", experimentRequest.getExpId());
		parameters.addValue("projectId", experimentRequest.getProjectId());
		parameters.addValue("teamId", experimentRequest.getTeamId());
		parameters.addValue("userId", experimentRequest.getUserId());
		parameters.addValue("experimentName", experimentRequest.getExperimentName());
		parameters.addValue("experimentStatus", experimentRequest.getExperimentStatus());
		parameters.addValue("summary", experimentRequest.getSummary());
		parameters.addValue("status", experimentRequest.getStatus());
		parameters.addValue("updateUser", experimentRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateExperimentQuery, parameters);
	}

	@Override
	public Integer deleteExperiment(Integer experimentId) {
		return jdbcTemplate.update(deleteExperimentQuery, new Object[] {experimentId});
	}
	
	class ExperimentRowMapper implements RowMapper<ExperimentDto> {
		public ExperimentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ExperimentDto experimentDto = new ExperimentDto();
			experimentDto.setExpId(resultSet.getInt("EXP_ID"));
			experimentDto.setExperimentName(resultSet.getString("EXPERIMENT_NAME"));
			experimentDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			experimentDto.setTeamId(resultSet.getInt("TEAM_ID"));
			experimentDto.setUserId(resultSet.getInt("USER_ID"));
			experimentDto.setExperimentStatus(resultSet.getString("EXPERIMENT_STATUS"));
			experimentDto.setSummary(resultSet.getString("SUMMARY"));
			experimentDto.setStatus(resultSet.getString("STATUS"));
			experimentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			experimentDto.setInsertUser(resultSet.getString("INSERT_USER"));
			experimentDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			experimentDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			
			return experimentDto;
		};
	}
}
