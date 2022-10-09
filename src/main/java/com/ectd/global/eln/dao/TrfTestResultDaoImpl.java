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

import com.ectd.global.eln.dto.TrfTestResultDto;
import com.ectd.global.eln.request.TrfTestResultRequest;

@Repository
@PropertySource(value = {"classpath:sql/test-request-form-dao.properties"})
public class TrfTestResultDaoImpl implements TrfTestResultDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getTrfTestResultById}")
	private String getTrfTestResultByIdQuery;

	@Value(value="${getTrfTestResultList}")
	private String getTrfTestResultListQuery;

	@Value(value="${createTrfTestResult}")
	private String createTrfTestResultQuery;

	@Value(value="${updateTrfTestResult}")
	private String updateTrfTestResultQuery;

	@Value(value="${deleteTrfTestResult}")
	private String deleteTrfTestResultQuery;

	@Override
	public TrfTestResultDto getTrfTestResultById(Integer trfTestResultId) {
		List<TrfTestResultDto> trfTestResults = jdbcTemplate.query(getTrfTestResultByIdQuery + trfTestResultId,
				new TrfTestResultMapper());

		if(trfTestResults.isEmpty()) {
			return null;
		}

		return trfTestResults.get(0);
	}

	@Override
	public List<TrfTestResultDto> getTrfTestResults() {
		return jdbcTemplate.query(getTrfTestResultListQuery, new TrfTestResultMapper());
	}

	@Override
	public Integer createTrfTestResult(TrfTestResultRequest trfTestResultRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("trfId", trfTestResultRequest.getTrfId());
		parameters.addValue("testId", trfTestResultRequest.getTestId());
		parameters.addValue("testStatus", trfTestResultRequest.getTestStatus());
		parameters.addValue("insertProcess", trfTestResultRequest.getInsertProcess());
		parameters.addValue("updateProcess", trfTestResultRequest.getUpdateProcess());
		parameters.addValue("insertDate", trfTestResultRequest.getInsertDate());
		parameters.addValue("updateDate", trfTestResultRequest.getUpdateDate());

		return namedParameterJdbcTemplate.update(createTrfTestResultQuery, parameters);
	}

	@Override
	public Integer updateTrfTestResult(TrfTestResultRequest trfTestResultRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("trfTestId", trfTestResultRequest.getTrfTestId());
		parameters.addValue("trfId", trfTestResultRequest.getTrfId());
		parameters.addValue("testId", trfTestResultRequest.getTestId());
		parameters.addValue("testStatus", trfTestResultRequest.getTestStatus());
		parameters.addValue("insertProcess", trfTestResultRequest.getInsertProcess());
		parameters.addValue("updateProcess", trfTestResultRequest.getUpdateProcess());
		parameters.addValue("insertDate", trfTestResultRequest.getInsertDate());
		parameters.addValue("updateDate", trfTestResultRequest.getUpdateDate());

		return namedParameterJdbcTemplate.update(updateTrfTestResultQuery, parameters);
	}

	@Override
	public Integer deleteTrfTestResult(Integer trfTestResultId) {
		return jdbcTemplate.update(deleteTrfTestResultQuery, new Object[] {trfTestResultId});
	}
	
	class TrfTestResultMapper implements RowMapper<TrfTestResultDto> {
		public TrfTestResultDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TrfTestResultDto trfTestResultDto = new TrfTestResultDto();
			trfTestResultDto.setTrfId(resultSet.getInt("TRF_ID"));
			trfTestResultDto.setTrfTestId(resultSet.getInt("TRF_TEST_ID"));
			trfTestResultDto.setTestId(resultSet.getInt("TEST_ID"));
			trfTestResultDto.setTestStatus(resultSet.getString("TEST_STATUS"));
			trfTestResultDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			trfTestResultDto.setInsertProcess(resultSet.getString("UPDATE_DATE"));
			trfTestResultDto.setInsertDate(resultSet.getDate("UPDATE_DATE"));
			trfTestResultDto.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));
			return trfTestResultDto;
		};
	}
	
}
