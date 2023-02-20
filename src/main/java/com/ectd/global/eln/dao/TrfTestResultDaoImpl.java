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
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/test-request-form-dao.properties"})
public class TrfTestResultDaoImpl implements TrfTestResultDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.trf.test.result.by.id}")
	private String GET_TRF_TEST_RESULT_BY_ID_QUERY;

	@Value(value="${get.trf.test.result.list}")
	private String GET_TRF_TEST_RESULT_LIST_QUERY;

	@Value(value="${create.trf.test.result}")
	private String CREATE_TRF_TEST_RESULT_QUERY;
	
	@Value("${update.trf.test.result}")
	private String UPDATE_TRF_TEST_RESULT_QUERY;

	@Value(value="${delete.trf.test.result}")
	private String DELETE_TRF_TEST_RESULT_QUERY;

	@Override
	public TrfTestResultDto getTrfTestResultById(Integer trfTestResultId) {
		List<TrfTestResultDto> trfTestResults = jdbcTemplate.query(GET_TRF_TEST_RESULT_BY_ID_QUERY + trfTestResultId,
				new TrfTestResultMapper());

		if(trfTestResults.isEmpty()) {
			return null;
		}

		return trfTestResults.get(0);
	}

	@Override
	public List<TrfTestResultDto> getTrfTestResults() {
		return jdbcTemplate.query(GET_TRF_TEST_RESULT_LIST_QUERY, new TrfTestResultMapper());
	}

	@Override
	public Integer createTrfTestResult(TrfTestResultRequest trfTestResultRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("trfId", trfTestResultRequest.getTrfId());
		parameters.addValue("testId", trfTestResultRequest.getTestId());
		parameters.addValue("testStatus", trfTestResultRequest.getTestStatus());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertUser", trfTestResultRequest.getInsertUser());
		parameters.addValue("updateUser", trfTestResultRequest.getUpdateUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(CREATE_TRF_TEST_RESULT_QUERY, parameters);
	}

	@Override
	public Integer updateTrfTestResult(TrfTestResultRequest trfTestResultRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("trfTestId", trfTestResultRequest.getTrfTestId());
		parameters.addValue("trfId", trfTestResultRequest.getTrfId());
		parameters.addValue("testId", trfTestResultRequest.getTestId());
		parameters.addValue("testStatus", trfTestResultRequest.getTestStatus());
		parameters.addValue("status", trfTestResultRequest.getStatus());
		parameters.addValue("updateUser", trfTestResultRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_TRF_TEST_RESULT_QUERY, parameters);
	}

	@Override
	public Integer deleteTrfTestResult(Integer trfTestResultId) {
		return jdbcTemplate.update(DELETE_TRF_TEST_RESULT_QUERY, new Object[] {trfTestResultId});
	}
	
	class TrfTestResultMapper implements RowMapper<TrfTestResultDto> {
		public TrfTestResultDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TrfTestResultDto trfTestResultDto = new TrfTestResultDto();
			trfTestResultDto.setTrfId(resultSet.getInt("TRF_ID"));
			trfTestResultDto.setTrfTestId(resultSet.getInt("TRF_TEST_ID"));
			trfTestResultDto.setTestId(resultSet.getInt("TEST_ID"));
			trfTestResultDto.setTestStatus(resultSet.getString("TEST_STATUS"));
			trfTestResultDto.setStatus(resultSet.getString("STATUS"));
			trfTestResultDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			trfTestResultDto.setInsertUser(resultSet.getString("UPDATE_DATE"));
			trfTestResultDto.setInsertDate(resultSet.getDate("UPDATE_DATE"));
			trfTestResultDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			return trfTestResultDto;
		};
	}
	
}
