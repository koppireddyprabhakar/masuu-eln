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
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.ExcipientRequest;
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

	@Value(value="${create.analysis.details}")
	private String CREATE_ANALYSIS_DETAILS_QUERY;

	@Value(value="${update.analysis.details}")
	private String UPDATE_ANALYSIS_DETAILS_QUERY;

	@Value(value = "${create.excipient}")
	private String CREATE_EXCIPIENT_QUERY;

	@Value(value="${update.excipient}")
	private String UPDATE_EXCIPIENT_QUERY;

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
		parameters.addValue("status", analysisRequest.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
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
		parameters.addValue("status", analysisRequest.getStatus());
		parameters.addValue("updateUser", analysisRequest.getUpdateUser());
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
			analysisDto.setStatus(resultSet.getString("STATUS"));
			analysisDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setInsertUser(resultSet.getString("INSERT_USER"));
			analysisDto.setUpdateDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setUpdateUser(resultSet.getString("INSERT_USER"));
			return  analysisDto;
		};
	}

	//CREATE ANALYSIS DETAILS
	@Override
	public int[] batchAnalysisDetailsInsert(List<AnalysisDetails> analysisDetailsList) {

		analysisDetailsList.forEach(ed -> {
			ed.setInsertDate(ElnUtils.getTimeStamp());
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(analysisDetailsList.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_ANALYSIS_DETAILS_QUERY, batch );
	}

	@Override
	public int[] batchExcipientInsert(List<ExcipientRequest> excipients) {

		excipients.stream().forEach(e -> {
			e.setInsertDate(ElnUtils.getTimeStamp());
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_EXCIPIENT_QUERY, batch);
	}

	@Override
	public int[] batchExcipientUpdate(List<ExcipientRequest> excipients) {

		excipients.stream().forEach(e -> {
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_EXCIPIENT_QUERY, batch);
	}

	@Override
	public int[] batchAnalysisDetailsUpdate(List<AnalysisDetails> analysisDetails) {

		analysisDetails.forEach(ed -> {
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(analysisDetails);
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_ANALYSIS_DETAILS_QUERY, batch);
	}

}
