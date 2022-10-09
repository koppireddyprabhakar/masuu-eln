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

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/analysis-dao.properties"})
public class AnalysisDaoImpl implements AnalysisDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getAnalysisById}")
	private String getAnalysisByIdQuery;

	@Value(value="${getAnalysisList}")
	private String getAnalysisListQuery;

	@Value(value="${createAnalysis}")
	private String createAnalysisQuery;

	@Value(value="${updateAnalysis}")
	private String updateAnalysisQuery;

	@Value(value="${deleteAnalysis}")
	private String deleteAnalysisQuery;

	@Override
	public AnalysisDto getAnalysisById(Integer analysisId) {
		List<AnalysisDto> analysisList = jdbcTemplate.query(getAnalysisByIdQuery + analysisId,
				new AnalysisRowMapper());

		if(analysisList.isEmpty()) {
			return null;
		}

		return analysisList.get(0);
	}

	@Override
	public List<AnalysisDto> getAnalysisList() {
		return jdbcTemplate.query(getAnalysisListQuery, new AnalysisRowMapper());
	}

	@Override
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisName", analysisRequest.getAnalysisName());
		parameters.addValue("projectId", analysisRequest.getProjectId());
		parameters.addValue("teamId", analysisRequest.getProjectId());
		parameters.addValue("expId", analysisRequest.getProjectId());
		parameters.addValue("summary", analysisRequest.getProjectId());
		parameters.addValue("insertProcess", analysisRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", analysisRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(createAnalysisQuery, parameters);
	}

	@Override
	public Integer updateAnalysis(AnalysisRequest analysisRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisRequest.getAnalysisId());
		parameters.addValue("analysisName", analysisRequest.getAnalysisName());
		parameters.addValue("projectId", analysisRequest.getProjectId());
		parameters.addValue("teamId", analysisRequest.getProjectId());
		parameters.addValue("expId", analysisRequest.getProjectId());
		parameters.addValue("summary", analysisRequest.getProjectId());
		parameters.addValue("updateProcess", analysisRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateAnalysisQuery, parameters);
	}

	
	@Override
	public Integer deleteAnalysis(Integer analysisId) {
		return jdbcTemplate.update(deleteAnalysisQuery, new Object[] {analysisId});
	}
	
	class AnalysisRowMapper implements RowMapper<AnalysisDto> {
		public AnalysisDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AnalysisDto analysisDto = new AnalysisDto();
			analysisDto.setAnalysisId(resultSet.getInt("ANALYSIS_ID"));
			analysisDto.setAnalysisName(resultSet.getString("ANALYSIS_NAME"));
			analysisDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			analysisDto.setTeamId(resultSet.getInt("TEAM_ID"));
			analysisDto.setExpId(resultSet.getInt("EXP_ID"));
			analysisDto.setSummary(resultSet.getString("SUMMARY"));
			analysisDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			analysisDto.setUpdateDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setUpdateProcess(resultSet.getString("INSERT_PROCESS"));
			return  analysisDto;
		};
	}

}
