package com.ectd.global.eln.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.AnalysisExcipientDto;
import com.ectd.global.eln.dto.AnalysisReviewDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.request.AnalysisExcipient;
import com.ectd.global.eln.request.AnalysisRequest;
import com.ectd.global.eln.request.AnalysisReview;
import com.ectd.global.eln.request.TestRequestFormRequest;
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

	@Value(value="${get.analysis.by.id}")
	private String GET_ANALYSIS_BY_ID_QUERY;

	@Value(value="${get.analysis.list}")
	private String GET_ANALYSIS_LIST_QUERY;

	@Value(value="${create.analysis}")
	private String CREATE_ANALYSIS_QUERY;

	@Value(value="${update.analysis}")
	private String UPDATE_ANALYSIS_QUERY;

	@Value(value="${delete.analysis}")
	private String DELETE_ANALYSIS_QUERY;

	@Value(value="${create.analysis.details}")
	private String CREATE_ANALYSIS_DETAILS_QUERY;

	@Value(value="${update.analysis.details}")
	private String UPDATE_ANALYSIS_DETAILS_QUERY;

	@Value(value = "${create.analysis.excipient}")
	private String CREATE_ANALYSIS_EXCIPIENT_QUERY;

	@Value(value="${update.analysis.excipient}")
	private String UPDATE_ANALYSIS_EXCIPIENT_QUERY;

	@Value("${update.trf}")
	private String UPDATE_TRF;

	@Value("${delete.analysis.excipient}")
	private String DELETE_ANALSIS_EXCIPIENT_QUERY;

	@Value("${trfs.by.analysi.id}")
	private String GET_TRFS_BY_ANALYSIS_ID_QUERY;

	@Value("${update.test.request.form.results}")
	private String UPDATE_TEST_REQUEST_FORM_RESULT_QUERY;

	@Value("${get.excipients.by.analysisId}")
	private String GET_EXCIPIENTS_BY_ANALYSISID_QUERY;

	@Value("${get.analysis.by.id.with.out.trf}")
	private String GET_ANALYSIS_BY_ID_WITH_OUT_TRF_QUERY;

	@Value("${update.analysis.status}")
	private String UPDATE_ANALYSIS_STATUS_QUERY;

	@Value("${update.trf.status}")
	private String UPDATE_TRF_STATUS_QUERY;

	@Value("${create.anlaysis.review}")
	private String CREATE_ANALYSIS_REVIEW_QUERY;

	@Value("${update.anlaysis.review}")
	private String UPDATE_ANALYSIS_REVIEW_QUERY;
	
	@Value("${get.anlaysis.review}")
	private String GET_ANALYSIS_REVIEW_QUERY;

	@Override
	public AnalysisDto getAnalysisById(Integer analysisId) {
		List<AnalysisDto> analysisList = jdbcTemplate.query(GET_ANALYSIS_BY_ID_WITH_OUT_TRF_QUERY + analysisId,
				new AnalysisExtractor());

		if(analysisList.isEmpty()) {
			return getAnalysisByIdWithoutTRF(analysisId);
		}

		return analysisList.get(0);
	}

	public List<AnalysisDto> getAnalysisExperiments(Integer analysisId) {
		List<AnalysisDto> analysisList = jdbcTemplate.query(GET_ANALYSIS_BY_ID_WITH_OUT_TRF_QUERY + analysisId,
				new AnalysisExtractor());

		return analysisList;
	}

	@Override
	public AnalysisDto getAnalysisByIdWithoutTRF(Integer analysisId) {
		List<AnalysisDto> analysisList = jdbcTemplate.query(GET_ANALYSIS_BY_ID_WITH_OUT_TRF_QUERY + analysisId,
				new AnalysisDetailsExtractor());

		if(analysisList.isEmpty()) {
			return null;
		}

		return analysisList.get(0);
	}


	@Override
	public List<AnalysisDto> getAnalysisList(Integer teamId, String status, Integer userID) {

		StringBuilder sb = new StringBuilder(GET_ANALYSIS_LIST_QUERY);

		if(userID != null) {
			sb.append(" AND USER_ID = ").append(userID);
		}
		
		if(teamId != null) {
			sb.append(" AND AE.TEAM_ID = " + teamId);
		}

		if(status != null) {

			sb.append(" AND AE.STATUS = ").append("'"+status+"'");
		}

		sb.append(" ORDER BY AE.INSERT_DATE DESC");

		return jdbcTemplate.query(sb.toString(), new AnalysisRowMapper());
	}

	@Override
	public Integer createAnalysis(AnalysisRequest analysisRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		parameters.addValue("analysisName", analysisRequest.getAnalysisName());
		parameters.addValue("projectId", analysisRequest.getProjectId());
		parameters.addValue("teamId", analysisRequest.getTeamId());
		parameters.addValue("summary", analysisRequest.getProjectId());
		parameters.addValue("status", AnalysisRequest.ANALYSIS_STATUS.INPROGRESS.getValue());
		parameters.addValue("userId", analysisRequest.getUserId());
		parameters.addValue("batchSize", analysisRequest.getBatchSize());
		parameters.addValue("batchNumber", analysisRequest.getBatchNumber());
		parameters.addValue("insertUser", analysisRequest.getUserId());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", analysisRequest.getUserId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(CREATE_ANALYSIS_QUERY, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public Integer updateAnalysis(AnalysisRequest analysisRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisRequest.getAnalysisId());
		parameters.addValue("analysisName", analysisRequest.getAnalysisName());
		parameters.addValue("projectId", analysisRequest.getProjectId());
		parameters.addValue("teamId", analysisRequest.getTeamId());
		parameters.addValue("summary", analysisRequest.getProjectId());
		parameters.addValue("status", analysisRequest.getStatus());
		parameters.addValue("userId", analysisRequest.getUserId());
		parameters.addValue("batchSize", analysisRequest.getBatchSize());
		parameters.addValue("batchNumber", analysisRequest.getBatchNumber());
		parameters.addValue("updateUser", analysisRequest.getUserId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_QUERY, parameters);
	}


	@Override
	public Integer deleteAnalysis(Integer analysisId) {
		return jdbcTemplate.update(DELETE_ANALYSIS_QUERY, new Object[] {analysisId});
	}

	@Override
	public Integer deleteAnalysisExcipient(Integer analysisId) {
		return jdbcTemplate.update(DELETE_ANALSIS_EXCIPIENT_QUERY + analysisId);
	}


	class AnalysisRowMapper implements RowMapper<AnalysisDto> {
		public AnalysisDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AnalysisDto analysisDto = new AnalysisDto();
			analysisDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
			analysisDto.setAnalysisName(resultSet.getString("ANALYSIS_NAME"));
			analysisDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			analysisDto.setTeamId(resultSet.getInt("TEAM_ID"));
			analysisDto.setSummary(resultSet.getString("SUMMARY"));
			analysisDto.setStatus(resultSet.getString("STATUS"));
			analysisDto.setBatchSize(resultSet.getString("BATCH_SIZE"));
			analysisDto.setBatchNumber(resultSet.getString("BATCH_NUMBER"));
			analysisDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setInsertUser(resultSet.getString("INSERT_USER"));
			analysisDto.setUpdateDate(resultSet.getDate("INSERT_DATE"));
			analysisDto.setUpdateUser(resultSet.getString("INSERT_USER"));

			ProjectDto projectDto = new ProjectDto();
			projectDto.setProjectId(resultSet.getInt("PROJECT_ID"));
			projectDto.setProjectName(resultSet.getString("PROJECT_NAME"));
			projectDto.setProductId(resultSet.getInt("PRODUCT_ID"));
			projectDto.setProductName(resultSet.getString("PRODUCT_NAME"));
			projectDto.setStrength(resultSet.getString("STRENGTH"));
			projectDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			projectDto.setDosageName(resultSet.getString("DOSAGE_NAME"));
			projectDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
			projectDto.setFormulationName(resultSet.getString("FORMULATION_NAME"));
			projectDto.setTeamName(resultSet.getString("TEAM_NAME"));
			projectDto.setMarketId(resultSet.getInt("MARKET_ID"));
			projectDto.setMarkertName(resultSet.getString("MARKET_NAME"));

			analysisDto.setProject(projectDto);

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
		List<Map<String, Object>> batchValues = new ArrayList<>(analysisDetailsList.size());
		for (AnalysisDetails analysisDetails : analysisDetailsList) {
			batchValues.add(
					new MapSqlParameterSource("analysisId", analysisDetails.getAnalysisId())
					.addValue("fileContent", analysisDetails.getFileContent().getBytes())
					.addValue("name", analysisDetails.getName())
					.addValue("status", analysisDetails.getStatus())
					.addValue("insertUser", "ELN")
					.addValue("insertDate", ElnUtils.getTimeStamp())
					.addValue("updateUser", "ELN")
					.addValue("updateDate", ElnUtils.getTimeStamp())
					.getValues());
		}

		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_ANALYSIS_DETAILS_QUERY, batchValues.toArray(new Map[analysisDetailsList.size()]) );

		//		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(analysisDetailsList.toArray());
		//		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_ANALYSIS_DETAILS_QUERY, batch );
	}

	@Override
	public Integer batchExcipientInsert(List<AnalysisExcipient> excipients) {

		excipients.stream().forEach(e -> {
			e.setInsertDate(ElnUtils.getTimeStamp());
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		int[] effectedRows = this.namedParameterJdbcTemplate.batchUpdate(CREATE_ANALYSIS_EXCIPIENT_QUERY, batch);
		return effectedRows.length;
	}

	@Override
	public int[] batchExcipientUpdate(List<AnalysisExcipient> excipients) {

		excipients.stream().forEach(e -> {
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_ANALYSIS_EXCIPIENT_QUERY, batch);
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

	@Override
	public int[] batchTRFUpdate(List<TestRequestFormRequest> testRequestFormList, Integer analysisExperimentId) {

		testRequestFormList.stream().forEach(e -> {
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		return this.jdbcTemplate.batchUpdate(UPDATE_TRF, 
				new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) 
					throws SQLException {
				ps.setInt(1, analysisExperimentId);
				ps.setString(2, TestRequestFormRequest.TRF_STATUS.INPROGRESS.getValue());
				ps.setString(3, ElnUtils.DEFAULT_USER_ID);
				ps.setTimestamp(4, ElnUtils.getTimeStamp());
				ps.setInt(5, testRequestFormList.get(i).getTestRequestFormId());
			}

			public int getBatchSize() {
				return testRequestFormList.size();
			}
		}
				);
	}

	@Override
	public Integer updateTRFStatus(Integer analysisExperimentId, String status) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisExperimentId);
		parameters.addValue("testRequestFormStatus", status);
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_TRF_STATUS_QUERY, parameters);
	}

	@Override
	public Integer createAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("analysisId", analysisExcipient.getAnalysisId());
		parameters.addValue("excipientId", analysisExcipient.getExcipientId());
		parameters.addValue("materialType", analysisExcipient.getMaterialType());
		parameters.addValue("materialName", analysisExcipient.getMaterialName());
		parameters.addValue("batchNo", analysisExcipient.getBatchNo());
		parameters.addValue("sourceName", analysisExcipient.getSourceName());
		parameters.addValue("potency", analysisExcipient.getPotency());
		parameters.addValue("grade", analysisExcipient.getGrade());
		parameters.addValue("status", analysisExcipient.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(CREATE_ANALYSIS_EXCIPIENT_QUERY, parameters);
	}

	@Override
	public Integer updateAnalysisExcipient(AnalysisExcipient analysisExcipient) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("analysisId", analysisExcipient.getAnalysisId());
		parameters.addValue("analysisExcipientId", analysisExcipient.getAnalysisExcipientId());
		parameters.addValue("excipientId", analysisExcipient.getExcipientId());
		parameters.addValue("materialType", analysisExcipient.getMaterialType());
		parameters.addValue("materialName", analysisExcipient.getMaterialName());
		parameters.addValue("batchNo", analysisExcipient.getBatchNo());
		parameters.addValue("sourceName", analysisExcipient.getSourceName());
		parameters.addValue("potency", analysisExcipient.getPotency());
		parameters.addValue("grade", analysisExcipient.getGrade());
		parameters.addValue("status", analysisExcipient.getStatus());
		parameters.addValue("updateUser", analysisExcipient.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_EXCIPIENT_QUERY, parameters);
	}

	@Override
	public Integer updateTestRequestFormResult(List<TestRequestFormRequest> results) {
		results.forEach(ed -> {
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(results);
		int[] effectedRows =  this.namedParameterJdbcTemplate.batchUpdate(UPDATE_TEST_REQUEST_FORM_RESULT_QUERY, batch);
		return effectedRows.length;
	}

	@Override
	public List<AnalysisExcipientDto> getExcipientByAnalysisId(Integer analysisId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisId);
		return namedParameterJdbcTemplate.query(GET_EXCIPIENTS_BY_ANALYSISID_QUERY, parameters, new AnalysisExcipientRowMapper());
	}

	@Override
	public Integer updateAnalysisStatus(AnalysisRequest analysisRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisRequest.getAnalysisId());
		parameters.addValue("status", analysisRequest.getStatus());
		parameters.addValue("summary", analysisRequest.getSummary());

		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_STATUS_QUERY, parameters);
	}

	@Override
	public List<TestRequestFormDto> getTestRequestByAnalysisId(Integer analysisId) {
		return jdbcTemplate.query(GET_TRFS_BY_ANALYSIS_ID_QUERY + analysisId, new TestRequestFormRowMapper());
	}

	@Override
	public Integer createAnalysisReview(AnalysisReview analysisReview) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("analysisId", analysisReview.getAnalysisId());
		parameters.addValue("reviewUserId", analysisReview.getReviewUserId());
		parameters.addValue("comments", analysisReview.getComments());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(CREATE_ANALYSIS_REVIEW_QUERY, parameters);
	}

	@Override
	public Integer updateAnalysisReview(AnalysisReview analysisReview) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("analysisReviewId", analysisReview.getAnalysisReviewId());
		parameters.addValue("analysisId", analysisReview.getAnalysisId());
		parameters.addValue("reviewUserId", analysisReview.getReviewUserId());
		parameters.addValue("comments", analysisReview.getComments());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_REVIEW_QUERY, parameters);
	}
	
	@Override
	public AnalysisReviewDto getAnalysisReview(Integer analysisId) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("analysisId", analysisId);
		
		List<AnalysisReviewDto> analysisReviewDtos = namedParameterJdbcTemplate.query(GET_ANALYSIS_REVIEW_QUERY, parameters, 
				new AnalysisReviewRowMapper());

		if(analysisReviewDtos.isEmpty()) {
			return null;
		}

		return analysisReviewDtos.get(0);
	}
	
	class AnalysisReviewRowMapper implements RowMapper<AnalysisReviewDto> {
		public AnalysisReviewDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AnalysisReviewDto analysisReviewDto = new AnalysisReviewDto();

			analysisReviewDto.setAnalysisReviewId(resultSet.getInt("ANALYSIS_REVIEW_ID"));
			analysisReviewDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
			analysisReviewDto.setComments(resultSet.getString("COMMENTS"));
			analysisReviewDto.setReviewUserId(resultSet.getInt("REVIEW_USER_ID"));
			
			return analysisReviewDto;
		};
	}

	class AnalysisExcipientRowMapper implements RowMapper<AnalysisExcipientDto> {
		public AnalysisExcipientDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AnalysisExcipientDto excipientDto = getAnalysisExcipientDto(resultSet);

			return excipientDto;
		};
	}


	class AnalysisExtractor implements ResultSetExtractor<List<AnalysisDto>> {

		@Override
		public List<AnalysisDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<AnalysisDto> analysisDtoList = new ArrayList<AnalysisDto>();

			while(resultSet.next()) {
				AnalysisDto analysisDto = getAnalysisDto(resultSet);

				AnalysisDetailsDto analysisDetails = getAnalysisDetailsWithOutContent(resultSet);

				if(CollectionUtils.contains(analysisDtoList.iterator(), analysisDto)) {
					int index = analysisDtoList.indexOf(analysisDto);
					analysisDtoList.get(index).getAnalysisDetails().add(analysisDetails);	
				} else {

					Set<AnalysisDetailsDto> analysisDetailsList = new LinkedHashSet<>();

					analysisDetailsList.add(analysisDetails);
					analysisDto.setAnalysisDetails(analysisDetailsList);
					analysisDtoList.add(analysisDto);
				}

			}
			return analysisDtoList;
		};
	}

	class AnalysisDetailsExtractor implements ResultSetExtractor<List<AnalysisDto>> {

		@Override
		public List<AnalysisDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<AnalysisDto> analysisDtoList = new ArrayList<AnalysisDto>();

			while(resultSet.next()) {
				AnalysisDto analysisDto = getAnalysisDto(resultSet);

				AnalysisDetailsDto analysisDetails = getAnalysisDetailsWithOutContent(resultSet);

				if(CollectionUtils.contains(analysisDtoList.iterator(), analysisDto)) {
					int index = analysisDtoList.indexOf(analysisDto);
					analysisDtoList.get(index).getAnalysisDetails().add(analysisDetails);
				} else {

					Set<AnalysisDetailsDto> analysisDetailsList = new LinkedHashSet<>();
					analysisDetailsList.add(analysisDetails);
					analysisDto.setAnalysisDetails(analysisDetailsList);

					analysisDtoList.add(analysisDto);
				}

			}
			return analysisDtoList;
		};
	}

	private AnalysisDto getAnalysisDto(ResultSet resultSet) throws SQLException {

		AnalysisDto analysisDto = new AnalysisDto();
		analysisDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
		analysisDto.setAnalysisName(resultSet.getString("ANALYSIS_NAME"));
		analysisDto.setProjectId(resultSet.getInt("PROJECT_ID"));
		analysisDto.setTeamId(resultSet.getInt("TEAM_ID"));
		analysisDto.setSummary(resultSet.getString("SUMMARY"));
		analysisDto.setStatus(resultSet.getString("STATUS"));
		analysisDto.setBatchSize(resultSet.getString("BATCH_SIZE"));
		analysisDto.setBatchNumber(resultSet.getString("BATCH_NUMBER"));

		//	analysisDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
		//	analysisDto.setInsertUser(resultSet.getString("INSERT_USER"));
		//	analysisDto.setUpdateDate(resultSet.getDate("INSERT_DATE"));
		//	analysisDto.setUpdateUser(resultSet.getString("INSERT_USER"));

		return  analysisDto;
	}

	private AnalysisDetailsDto getAnalysisDetailsWithOutContent(ResultSet resultSet) throws SQLException {

		AnalysisDetailsDto analysisDetails = new AnalysisDetailsDto();
		analysisDetails.setAnalysisDetailId(resultSet.getInt("ANALYSIS_EXP_DTL_ID"));
		analysisDetails.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
		analysisDetails.setName(resultSet.getString("NAME"));
		analysisDetails.setStatus(resultSet.getString("STATUS"));

		return analysisDetails;
	}

	private AnalysisExcipientDto getAnalysisExcipientDto(ResultSet resultSet) throws SQLException {

		AnalysisExcipientDto analysisExcipientDto = new AnalysisExcipientDto();
		analysisExcipientDto.setAnalysisExcipientId(resultSet.getInt("ANALYSIS_EXCIPIENT_ID"));
		analysisExcipientDto.setExcipientId(resultSet.getInt("EXCIPIENT_ID"));
		analysisExcipientDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
		analysisExcipientDto.setMaterialType(resultSet.getString("MATERIAL_TYPE"));
		analysisExcipientDto.setMaterialName(resultSet.getString("MATERIAL_NAME"));
		analysisExcipientDto.setBatchNo(resultSet.getString("BATCH_NO"));
		analysisExcipientDto.setSourceName(resultSet.getString("SOURCE_NAME"));
		analysisExcipientDto.setPotency(resultSet.getString("POTENCY"));
		analysisExcipientDto.setGrade(resultSet.getString("GRADE"));
		analysisExcipientDto.setStatus(resultSet.getString("STATUS"));
		analysisExcipientDto.setExcipientsName(resultSet.getString("EXCIPIENTS_NAME"));

		return analysisExcipientDto;
	}

	private TestRequestFormDto getTestRequestFormDto(ResultSet resultSet) throws SQLException {

		TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
		testRequestFormDto.setTestRequestFormId(resultSet.getInt("TRF_ID"));
		testRequestFormDto.setExpId(resultSet.getInt("EXP_ID"));
		testRequestFormDto.setTestId(resultSet.getInt("TEST_ID"));
		testRequestFormDto.setTestName(resultSet.getString("TEST_NAME"));
		testRequestFormDto.setTestNumber(resultSet.getString("TEST_NUMBER"));
		testRequestFormDto.setTestResult(resultSet.getString("TEST_RESULT"));
		testRequestFormDto.setTestStatus(resultSet.getString("TEST_STATUS"));

		return testRequestFormDto;
	}

	class TestRequestFormRowMapper implements RowMapper<TestRequestFormDto> {
		public TestRequestFormDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
			testRequestFormDto.setTestRequestFormId(resultSet.getInt("TRF_ID"));
			testRequestFormDto.setExpId(resultSet.getInt("EXP_ID"));
			testRequestFormDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
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

			return testRequestFormDto;
		};
	}


}
