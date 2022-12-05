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

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;
import com.ectd.global.eln.request.TrfTestResultRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/test-request-form-dao.properties"})
public class TestRequestFormDaoImpl implements TestRequestFormDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.test.request.form.by.id}")
	private String GET_TEST_REQUEST_FORM_BY_ID_QUERY;

	@Value(value="${get.test.request.form.list}")
	private String GET_TEST_REQUEST_FORM_LIST_QUERY;

	@Value(value="${create.test.request.form}")
	private String CREATE_TEST_REQUEST_FORM_QUERY;

	@Value(value="${update.test.request.form}")
	private String UPDATE_TEST_REQUEST_FORM_QUERY;

	@Value(value="${delete.test.request.form}")
	private String DELETE_TEST_REQUEST_FORM_QUERY;

	@Value(value="${create.trf.test.result}")
	private String CREATE_TRF_TEST_RESULT;

	@Value("${update.trf.test.result}")
	private String UPDATE_TRF_TEST_RESULT;

	@Override
	public TestRequestFormDto getTestRequestFormById(Integer testRequestFormId) {
		List<TestRequestFormDto> testRequestForms = jdbcTemplate.query(GET_TEST_REQUEST_FORM_BY_ID_QUERY + testRequestFormId,
				new TestRequestFormRowMapper());

		if(testRequestForms.isEmpty()) {
			return null;
		}

		return testRequestForms.get(0);
	}

	@Override
	public List<TestRequestFormDto> getTestRequestForms() {
		return jdbcTemplate.query(GET_TEST_REQUEST_FORM_LIST_QUERY, new TestRequestFormRowMapper());
	}

	@Override
	public Integer createTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();

		parameters.addValue("expId", testRequestFormRequest.getExpId());
		//		parameters.addValue("projectId", testRequestFormRequest.getProjectId());
		//		parameters.addValue("analysisId", testRequestFormRequest.getAnalysisId());
		parameters.addValue("testRequestFormStatus", testRequestFormRequest.getTestRequestFormStatus());
		parameters.addValue("condition", testRequestFormRequest.getCondition());
		parameters.addValue("stage", testRequestFormRequest.getStage());
		parameters.addValue("packaging", testRequestFormRequest.getPackaging());
		parameters.addValue("labelClaim", testRequestFormRequest.getLabelClaim());
		parameters.addValue("quantity", testRequestFormRequest.getQuantity());
		parameters.addValue("manufacturingDate", testRequestFormRequest.getManufacturingDate());
		parameters.addValue("testId", testRequestFormRequest.getTestId());
		parameters.addValue("testName", testRequestFormRequest.getTestName());
		parameters.addValue("testNumber", testRequestFormRequest.getTestNumber());
		parameters.addValue("testResult", testRequestFormRequest.getTestResult());
		parameters.addValue("testStatus", testRequestFormRequest.getTestStatus());
		parameters.addValue("status", testRequestFormRequest.getStatus());
		parameters.addValue("expireDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", testRequestFormRequest.getInsertUser());
		parameters.addValue("updateUser", testRequestFormRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(CREATE_TEST_REQUEST_FORM_QUERY, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testRequestFormId", testRequestFormRequest.getTestRequestFormId());
		parameters.addValue("expId", testRequestFormRequest.getExpId());
		//		parameters.addValue("projectId", testRequestFormRequest.getProjectId());
		//		parameters.addValue("analysisId", testRequestFormRequest.getAnalysisId());
		parameters.addValue("testRequestFormStatus", testRequestFormRequest.getTestRequestFormStatus());
		parameters.addValue("condition", testRequestFormRequest.getCondition());
		parameters.addValue("stage", testRequestFormRequest.getStage());
		parameters.addValue("packaging", testRequestFormRequest.getPackaging());
		parameters.addValue("labelClaim", testRequestFormRequest.getLabelClaim());
		parameters.addValue("quantity", testRequestFormRequest.getQuantity());
		parameters.addValue("testId", testRequestFormRequest.getTestId());
		parameters.addValue("testName", testRequestFormRequest.getTestName());
		parameters.addValue("testNumber", testRequestFormRequest.getTestNumber());
		parameters.addValue("testResult", testRequestFormRequest.getTestResult());
		parameters.addValue("testStatus", testRequestFormRequest.getTestStatus());
		parameters.addValue("status", testRequestFormRequest.getStatus());
		parameters.addValue("manufacturingDate", testRequestFormRequest.getManufacturingDate());
		parameters.addValue("expireDate", testRequestFormRequest.getExpireDate());
		parameters.addValue("updateUser", testRequestFormRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_TEST_REQUEST_FORM_QUERY, parameters);
	}

	@Override
	public Integer deleteTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		return this.updateTestRequestForm(testRequestFormRequest);
	}

	public int[] batchInsert(List<TrfTestResultRequest> trfTestResultRequestList) {
		trfTestResultRequestList.forEach(ed -> {
			ed.setInsertDate(ElnUtils.getTimeStamp());
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(trfTestResultRequestList.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_TRF_TEST_RESULT, batch );
	}

	public int[] batchUpdate(List<TrfTestResultRequest> trfTestResultRequestList) {

		trfTestResultRequestList.forEach(ed -> {
			ed.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(trfTestResultRequestList.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_TRF_TEST_RESULT, batch );
	}

	class TestRequestFormRowMapper implements RowMapper<TestRequestFormDto> {
		public TestRequestFormDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
			testRequestFormDto.setTestRequestFormId(resultSet.getInt("TRF_ID"));
			testRequestFormDto.setExpId(resultSet.getInt("EXP_ID"));
			//			testRequestFormDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			//			testRequestFormDto.setAnalysisId(resultSet.getInt("ANALYSIS_ID"));
			testRequestFormDto.setTestRequestFormStatus(resultSet.getString("TRF_STATUS"));
			testRequestFormDto.setCondition(resultSet.getString("CONDITION"));
			testRequestFormDto.setStage(resultSet.getString("STAGE"));
			testRequestFormDto.setPackaging(resultSet.getString("PACKAGING"));
			testRequestFormDto.setLabelClaim(resultSet.getString("LABEL_CLAIM"));
			testRequestFormDto.setQuantity(resultSet.getInt("QUANTITY"));
			testRequestFormDto.setManufacturingDate(resultSet.getDate("MANUFACTURING_DATE"));
			testRequestFormDto.setExpireDate(resultSet.getDate("EXPIRE_DATE"));
			testRequestFormDto.setTestId(resultSet.getInt("TEST_ID"));
			testRequestFormDto.setTestName(resultSet.getString("TEST_NAME"));
			testRequestFormDto.setTestNumber(resultSet.getString("TEST_NUMBER"));
			testRequestFormDto.setTestResult(resultSet.getString("TEST_RESULT"));
			testRequestFormDto.setTestStatus(resultSet.getString("TEST_STATUS"));
			testRequestFormDto.setStatus(resultSet.getString("STATUS"));		
			testRequestFormDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			testRequestFormDto.setInsertUser(resultSet.getString("INSERT_USER"));
			testRequestFormDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			testRequestFormDto.setUpdateUser(resultSet.getString("UPDATE_USER"));

			return testRequestFormDto;
		};
	}

}
