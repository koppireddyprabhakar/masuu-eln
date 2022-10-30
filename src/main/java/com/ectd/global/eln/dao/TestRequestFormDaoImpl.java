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

import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.TestRequestFormRequest;
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
	
	@Value(value="${getTestRequestFormById}")
	private String getTestRequestFormByIdQuery;
	
	@Value(value="${getTestRequestFormList}")
	private String getTestRequestFormListQuery;
	
	@Value(value="${createTestRequestForm}")
	private String createTestRequestFormQuery;
	
	@Value(value="${updateTestRequestForm}")
	private String updateTestRequestFormQuery;
	
	@Value(value="${deleteTestRequestForm}")
	private String deleteTestRequestFormQuery;

	@Override
	public TestRequestFormDto getTestRequestFormById(Integer testRequestFormId) {
		List<TestRequestFormDto> testRequestForms = jdbcTemplate.query(getTestRequestFormByIdQuery + testRequestFormId,
				new TestRequestFormRowMapper());
		
		if(testRequestForms.isEmpty()) {
			return null;
		}
		
		return testRequestForms.get(0);
	}

	@Override
	public List<TestRequestFormDto> getTestRequestForms() {
		return jdbcTemplate.query(getTestRequestFormListQuery, new TestRequestFormRowMapper());
	}

	@Override
	public Integer createTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("expId", testRequestFormRequest.getExpId());
		parameters.addValue("projectId", testRequestFormRequest.getProjectId());
		parameters.addValue("analysisId", testRequestFormRequest.getAnalysisId());
		parameters.addValue("testRequestFormStatus", testRequestFormRequest.getTestRequestFormStatus());
		parameters.addValue("condition", testRequestFormRequest.getCondition());
		parameters.addValue("stage", testRequestFormRequest.getStage());
		parameters.addValue("packaging", testRequestFormRequest.getPackaging());
		parameters.addValue("quantity", testRequestFormRequest.getQuantity());
		parameters.addValue("manufacturingDate", testRequestFormRequest.getManufacturingDate());
		parameters.addValue("status", testRequestFormRequest.getStatus());
		parameters.addValue("expireDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", testRequestFormRequest.getInsertUser());
		parameters.addValue("updateUser", testRequestFormRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(createTestRequestFormQuery, parameters);
	}
	
	@Override
	public Integer updateTestRequestForm(TestRequestFormRequest testRequestFormRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testRequestFormId", testRequestFormRequest.getTestRequestFormId());
		parameters.addValue("expId", testRequestFormRequest.getExpId());
		parameters.addValue("projectId", testRequestFormRequest.getProjectId());
		parameters.addValue("analysisId", testRequestFormRequest.getAnalysisId());
		parameters.addValue("testRequestFormStatus", testRequestFormRequest.getTestRequestFormStatus());
		parameters.addValue("condition", testRequestFormRequest.getCondition());
		parameters.addValue("stage", testRequestFormRequest.getStage());
		parameters.addValue("packaging", testRequestFormRequest.getPackaging());
		parameters.addValue("quantity", testRequestFormRequest.getQuantity());
		parameters.addValue("status", testRequestFormRequest.getStatus());
		parameters.addValue("manufacturingDate", testRequestFormRequest.getManufacturingDate());
		parameters.addValue("expireDate", testRequestFormRequest.getExpireDate());
		parameters.addValue("updateUser", testRequestFormRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(updateTestRequestFormQuery, parameters);
	}

	@Override
	public Integer deleteTestRequestForm(Integer testRequestFormId) {
		return jdbcTemplate.update(deleteTestRequestFormQuery, new Object[] {testRequestFormId});
	}
	
	class TestRequestFormRowMapper implements RowMapper<TestRequestFormDto> {
		public TestRequestFormDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
			testRequestFormDto.setTestRequestFormId(resultSet.getInt("TRF_ID"));
			testRequestFormDto.setExpId(resultSet.getInt("EXP_ID"));
			testRequestFormDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			testRequestFormDto.setAnalysisId(resultSet.getInt("ANALYSIS_ID"));
			testRequestFormDto.setTestRequestFormStatus(resultSet.getString("TRF_STATUS"));
			testRequestFormDto.setCondition(resultSet.getString("CONDITION"));
			testRequestFormDto.setStage(resultSet.getString("STAGE"));
			testRequestFormDto.setPackaging(resultSet.getString("PACKAGING"));
			testRequestFormDto.setQuantity(resultSet.getInt("QUANTITY"));
			testRequestFormDto.setManufacturingDate(resultSet.getDate("MANUFACTURING_DATE"));
			testRequestFormDto.setExpireDate(resultSet.getDate("EXPIRE_DATE"));
			testRequestFormDto.setStatus(resultSet.getString("STATUS"));		
			testRequestFormDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			testRequestFormDto.setInsertUser(resultSet.getString("INSERT_USER"));
			testRequestFormDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			testRequestFormDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			
		return testRequestFormDto;
		};
		}
	
}
