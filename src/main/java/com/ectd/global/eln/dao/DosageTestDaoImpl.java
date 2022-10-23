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

import com.ectd.global.eln.dto.DosageTestDto;
import com.ectd.global.eln.request.DosageTestRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/dosage-dao.properties"})
public class DosageTestDaoImpl implements DosageTestDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getDosageTestById}")
	private String getDosageTestByIdQuery;

	@Value(value="${getDosageTestList}")
	private String getDosageTestListQuery;

	@Value(value="${createDosageTest}")
	private String createDosageTestQuery;

	@Value(value="${updateDosageTest}")
	private String updateDosageTestQuery;

	@Value(value="${deleteDosageTest}")
	private String deleteDosageTestQuery;

	@Override
	public DosageTestDto getDosageTestById(Integer dosageTestId) {
		List<DosageTestDto> dosageTests = jdbcTemplate.query(getDosageTestByIdQuery + dosageTestId,
				new DosageTestRowMapper());

		if(dosageTests.isEmpty()) {
			return null;
		}

		return dosageTests.get(0);
	}

	@Override
	public List<DosageTestDto> getDosageTests() {
		return jdbcTemplate.query(getDosageTestListQuery, new DosageTestRowMapper());
	}

	@Override
	public Integer createDosageTest(DosageTestRequest dosageTestRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testId", dosageTestRequest.getTestId());
		parameters.addValue("dosageId", dosageTestRequest.getDosageId());
		parameters.addValue("insertProcess", dosageTestRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", dosageTestRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(createDosageTestQuery, parameters);
	}

	@Override
	public Integer updateDosageTest(DosageTestRequest dosageTestRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("testId", dosageTestRequest.getTestId());
		parameters.addValue("dosageId", dosageTestRequest.getDosageId());
		parameters.addValue("updateProcess", dosageTestRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(updateDosageTestQuery, parameters);
	}

	@Override
	public Integer deleteDosageTest(Integer dosageTestId) {
		return jdbcTemplate.update(deleteDosageTestQuery, new Object[] {dosageTestId});
	}
	
	class DosageTestRowMapper implements RowMapper<DosageTestDto> {
		public DosageTestDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DosageTestDto dosageTestDto = new DosageTestDto();
			dosageTestDto.setTestId(resultSet.getInt("TEST_ID"));
			dosageTestDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			dosageTestDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			dosageTestDto.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			dosageTestDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			dosageTestDto.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));
			
			return dosageTestDto;
		};
	}
	
}
