package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

import com.ectd.global.eln.dto.DosageTestDto;
import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.dto.TestDto;
import com.ectd.global.eln.request.DosageTestRequest;
import com.ectd.global.eln.request.TestRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/lab-test-dao.properties"})
public class LabTestDaoImpl implements LabTestDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getTestById}")
	private String getTestByIdQuery;

	@Value(value="${getTestList}")
	private String getTestListQuery;

	@Value(value="${createTest}")
	private String createTestQuery;

	@Value(value="${updateTest}")
	private String updateTestQuery;

	@Value(value="${deleteTest}")
	private String deleteTestQuery;

	@Value(value="${createDosageTest}")
	private String createDosageTestQuery;

	@Value(value="${updateDosageTest}")
	private String updateDosageTestQuery;
	
	@Override
	public TestDto getTestById(Integer testId) {
		List<TestDto> tests = jdbcTemplate.query(getTestByIdQuery + testId,
				new TestRowMapper());

		if(tests.isEmpty()) {
			return null;
		}

		return tests.get(0);
	}

	@Override
	public List<TestDto> getTests() {
		return jdbcTemplate.query(getTestListQuery, new TestRowMapper());
	}

	@Override
	public Integer createTest(TestRequest testRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testName", testRequest.getTestName());
		parameters.addValue("description", testRequest.getDescription());
		parameters.addValue("status", testRequest.getStatus());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp(), Types.TIMESTAMP);
		parameters.addValue("insertUser", testRequest.getInsertUser());
		parameters.addValue("updateUser", testRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp(), Types.TIMESTAMP);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(createTestQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public Integer updateTest(TestRequest testRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testId", testRequest.getTestId());
		parameters.addValue("testName", testRequest.getTestName());
		parameters.addValue("status", testRequest.getStatus());
		parameters.addValue("description", testRequest.getDescription());
		parameters.addValue("updateUser", testRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateTestQuery, parameters);
	}

	@Override
	public Integer deleteTest(Integer testId) {
		return jdbcTemplate.update(deleteTestQuery, new Object[] {testId});
	}

	@Override
	public Boolean createTests(List<TestRequest> testRequests) {

		for(TestRequest request: testRequests) {
			Integer testId = this.createTest(request);

			request.getDosageTestReqeustList().stream().forEach(dt -> {
				dt.setTestId(testId);
				dt.setInsertDate(ElnUtils.getTimeStamp());
				dt.setInsertUser("ELN");
				dt.setUpdateDate(ElnUtils.getTimeStamp());
				dt.setUpdateUser("ELN");
			});

			this.batchExecution(request.getDosageTestReqeustList(), createDosageTestQuery);
		}

		return true;
	}

	@Override
	public Boolean updateTests(TestRequest testRequest) {

		this.updateTest(testRequest);

		List<DosageTestRequest> updateDosageTests = testRequest.getDosageTestReqeustList().stream().filter(dt -> !(ObjectUtils.isEmpty(dt.getTestId())) ).collect(Collectors.toList());
		List<DosageTestRequest> insertDosageTests = testRequest.getDosageTestReqeustList().stream().filter(dt -> ObjectUtils.isEmpty(dt.getTestId())).collect(Collectors.toList());

		int[] updatedRows = null;
		if(!CollectionUtils.isEmpty(updateDosageTests)) {

			updateDosageTests.stream().forEach(dt -> {
				dt.setUpdateDate(ElnUtils.getTimeStamp());
				dt.setUpdateUser("ELN");
			});

			updatedRows = this.batchExecution(testRequest.getDosageTestReqeustList(), updateDosageTestQuery);;
		}

		if(!CollectionUtils.isEmpty(insertDosageTests)) {
			insertDosageTests.stream().forEach(dt -> {
				dt.setUpdateDate(ElnUtils.getTimeStamp());
				dt.setUpdateUser("ELN");
				dt.setInsertUser("ELN");
				dt.setInsertDate(ElnUtils.getTimeStamp());
			});
			this.batchExecution(testRequest.getDosageTestReqeustList(), updateDosageTestQuery);
		}


		if(updatedRows != null && updatedRows.length > 0) {
			return true;
		}

		return false;
	}
	
	@Override
	public Boolean deleteTests(TestRequest testRequest) {

		int[] count = this.batchExecution(testRequest.getDosageTestReqeustList(), updateDosageTestQuery);
	
		if(count.length > 0) {	
			this.deleteTest(testRequest.getTestId());
		}
		return true;
	}
	
	private int[] batchExecution(List<DosageTestRequest> dosageTestRequests, String query) {
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(dosageTestRequests.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(query, batch);
	}

	class TestRowMapper implements RowMapper<TestDto> {
		public TestDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TestDto testDto = new TestDto();
			testDto.setTestId(resultSet.getInt("TEST_ID"));
			testDto.setTestName(resultSet.getString("TEST_NAME"));
			testDto.setDescription(resultSet.getString("DESCRIPTION"));
			testDto.setStatus(resultSet.getString("STATUS"));
			testDto.setInsertUser(resultSet.getString("INSERT_USER"));
			testDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			testDto.setUpdateUser(resultSet.getString("UPDATE_USER"));

			return testDto;
		};
	}

}
