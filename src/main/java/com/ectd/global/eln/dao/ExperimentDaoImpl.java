package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
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

import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.ectd.global.eln.dto.ExperimentReviewDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.ExperimentReview;
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

	@Value(value="${get.experiment.by.id}")
	private String GET_EXPERIMENT_BY_ID_QUERY;
	
	@Value("${get.experiment.by.ids}")
	private String GET_EXPERIMENT_BY_IDs_QUERY;
	
	@Value(value="${get.experiment.list}")
	private String GET_EXPERIMENT_LIST_QUERY;

	@Value(value="${create.experiment}")
	private String CREATE_EXPERIMENT_QUERY;

	@Value(value="${update.experiment}")
	private String UPDATE_EXPERIMENT_QUERY;

	@Value(value="${delete.experiment}")
	private String DELETE_EXPERIMENT_QUERY;

	@Value(value="${create.experiment.details}")
	private String CREATE_EXPERIMENT_DETAILS_QUERY;

	@Value(value = "${create.experiment.excipient}")
	private String CREATE_EXPERIMENT_EXCIPIENT_QUERY;

	@Value(value="${update.experiment.excipient}")
	private String UPDATE_EXPERIMENT_EXCIPIENT_QUERY;

	@Value(value="${update.experiment.details}")
	private String UPDATE_EXPERIMENT_DETAILS_QUERY;

	@Value("${get.experiment.project}")
	private String GET_EXPERIMENT_PROJECT_QUERY;

	@Value("${get.experiment.info}")
	private String GET_EXPERIMENT_INFO_QUERY;

	@Value("${update.experiment.status}")
	private String UPDATE_EXPERIMENT_STATUS_QUERY;
	
	@Value("${delete.experiment.excipient}")
	private String DELETE_EXCIPIENT_QUERY;
	
	@Value("${get.excipients.by.experimentId}")
	private String GET_EXCIPIENTS_BY_EXPERIMENT_ID;
	
	@Value("${get.trf.by.exp.id}")
	private String GET_TRF_BY_EXPR_ID_QUERY;
	
	@Value("${create.experiment.review}")
	private String CREATE_EXPERIMENT_REVIEW_QUERY;

	@Value("${update.experiment.review}")
	private String UPDATE_EXPERIMENT_REVIEW_QUERY;
	
	@Value("${get.experiment.review}")
	private String GET_EXPERIMENT_REVIEW_QUERY;

	@Override
	public ExperimentDto getExperimentById(Integer experimentId) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("experimentId", experimentId);
		
		List<ExperimentDto> experiments = namedParameterJdbcTemplate.query(GET_EXPERIMENT_BY_ID_QUERY, params, new ExperimentExtractor());

		if(experiments.isEmpty()) {
			return null;
		}

		return experiments.get(0);
	}
	
	@Override
	public List<ExperimentDto> getExperimentByIds(String experimentId) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("experimentId", experimentId);
		String qry = GET_EXPERIMENT_BY_IDs_QUERY + " (" + experimentId + ")";
		return namedParameterJdbcTemplate.query(qry, new ExperimentExtractor());
	}

	@Override
	public List<TestRequestFormDto> getTRFByExpIds(Integer experimentId) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("experimentId", experimentId);
		
		return namedParameterJdbcTemplate.query(GET_TRF_BY_EXPR_ID_QUERY, params, new TestRequestFormRowMapper());
	}
	
	@Override
	public List<ExperimentDto> getExperiments(Integer userId, String status) {

		StringBuilder sb = new StringBuilder(GET_EXPERIMENT_LIST_QUERY);

		if(userId != null) {
			sb.append(" AND USER_ID = ").append(userId);
		}
		
		if(status != null) {
			sb.append(" AND EXPERIMENT_STATUS = ").append("'"+status+"'");
		}

		sb.append(" ORDER BY INSERT_DATE DESC");

		return jdbcTemplate.query(sb.toString(), new ExperimentRowMapper());
	}

	@Override
	public Integer createExperiment(ExperimentRequest experimentRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		parameters.addValue("projectId", experimentRequest.getProjectId());
		parameters.addValue("teamId", experimentRequest.getTeamId());
		parameters.addValue("userId", experimentRequest.getUserId());
		parameters.addValue("experimentName", experimentRequest.getExperimentName());
		parameters.addValue("experimentStatus", ExperimentRequest.EXPERIMENT_STATUS.INPROGRESS.getValue());
		parameters.addValue("summary", experimentRequest.getSummary());
		parameters.addValue("batchSize", experimentRequest.getBatchSize());
		parameters.addValue("batchNumber", experimentRequest.getBatchNumber());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertUser", experimentRequest.getUserId());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", experimentRequest.getUserId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(CREATE_EXPERIMENT_QUERY, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public int[] batchInsert(List<ExperimentDetails> experimentDetailsList) {

		experimentDetailsList.forEach(ed -> {
			ed.setInsertDate(ElnUtils.getTimeStamp());
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});
				
		List<Map<String, Object>> batchValues = new ArrayList<>(experimentDetailsList.size());
		for (ExperimentDetails experimentDetails : experimentDetailsList) {
		    batchValues.add(
		            new MapSqlParameterSource("experimentId", experimentDetails.getExperimentId())
		                    .addValue("fileContent", experimentDetails.getFileContent().getBytes())
		                    .addValue("name", experimentDetails.getName())
		                    .addValue("status", ElnUtils.STATUS.ACTIVE.getValue())
		                    .addValue("insertUser", "ELN")
		            		.addValue("insertDate", ElnUtils.getTimeStamp())
		            		.addValue("updateUser", "ELN")
		            		.addValue("updateDate", ElnUtils.getTimeStamp())
		                    .getValues());
		}
		
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_EXPERIMENT_DETAILS_QUERY, batchValues.toArray(new Map[experimentDetailsList.size()]) );
	}

	@Override
	public Integer batchExcipientInsert(List<ExcipientRequest> excipients) {

		excipients.stream().forEach(e -> {
			e.setInsertDate(ElnUtils.getTimeStamp());
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setInsertUser(ElnUtils.DEFAULT_USER_ID);
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		
		int[] excipientsInsert = this.namedParameterJdbcTemplate.batchUpdate(CREATE_EXPERIMENT_EXCIPIENT_QUERY, batch);
		
		return excipientsInsert.length;
	}

	@Override
	public Integer batchExcipientUpdate(List<ExcipientRequest> excipients) {

		excipients.stream().forEach(e -> {
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		int[] excipientsUpdatedRows = this.namedParameterJdbcTemplate.batchUpdate(UPDATE_EXPERIMENT_EXCIPIENT_QUERY, batch);
		
		return excipientsUpdatedRows.length;
	}

	@Override
	public int[] batchUpdate(List<ExperimentDetails> experimentDetails) {

		experimentDetails.forEach(ed -> {
			ed.setUpdateDate(ElnUtils.getTimeStamp());
			ed.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(experimentDetails);
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_EXPERIMENT_DETAILS_QUERY, batch);
	}

	@Override
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("expId", experimentRequest.getExperimentId());
		parameters.addValue("projectId", experimentRequest.getProjectId());
		parameters.addValue("teamId", experimentRequest.getTeamId());
		parameters.addValue("userId", experimentRequest.getUserId());
		parameters.addValue("experimentName", experimentRequest.getExperimentName());
		parameters.addValue("experimentStatus", experimentRequest.getExperimentStatus());
		parameters.addValue("summary", experimentRequest.getSummary());
		parameters.addValue("batchSize", experimentRequest.getBatchSize());
		parameters.addValue("batchNumber", experimentRequest.getBatchNumber());
		parameters.addValue("status", experimentRequest.getStatus());
		parameters.addValue("updateUser", experimentRequest.getUserId());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_QUERY, parameters);
	}

	@Override
	public Integer deleteExperiment(Integer experimentId) {
		return jdbcTemplate.update(DELETE_EXPERIMENT_QUERY, new Object[] {experimentId});
	}
	
	@Override
	public Integer deleteExperimentExcipient(Integer experimentId) {
		return jdbcTemplate.update(DELETE_EXCIPIENT_QUERY, new Object[] {experimentId});
	}
	
	@Override
	public List<ExperimentDto> getExperimentsWithProject() {
		return jdbcTemplate.query(GET_EXPERIMENT_PROJECT_QUERY, new ExperimentProjectRowMapper());
	}

	@Override
	public List<ExperimentDto> getExperimentsInfo(Integer experimentId) {
		return jdbcTemplate.query(GET_EXPERIMENT_INFO_QUERY + experimentId, new ExperimentIdRowMapper());
	}

	@Override
	public Integer updateExperimentStatus(Integer experimentId, String status) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("experimentId", experimentId);
		parameters.addValue("experimentStatus", status);
		parameters.addValue("status", status);
		
		if(ExperimentRequest.EXPERIMENT_STATUS.ANLYSIS_SUBMIT.getValue().equals(status)) {
			parameters.addValue("analysisSubmitDate", ElnUtils.getTimeStamp());	
		} else {
			parameters.addValue("analysisSubmitDate", null);
		}

		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_STATUS_QUERY, parameters);
	}
	
	@Override
	public List<ExperimentExcipientDto> getExcipientByExperimentId(Integer experimentId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("experimentId", experimentId);
		return namedParameterJdbcTemplate.query(GET_EXCIPIENTS_BY_EXPERIMENT_ID, parameters, new ExperimentExcipientRowMapper());
	}
	
	@Override
	public Integer createExperimentReview(ExperimentReview experimentReview) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("experimentId", experimentReview.getExperimentId());
		parameters.addValue("reviewUserId", experimentReview.getReviewUserId());
		parameters.addValue("comments", experimentReview.getComments());
		parameters.addValue("reviewType", experimentReview.getReviewType());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(CREATE_EXPERIMENT_REVIEW_QUERY, parameters);
	}

	@Override
	public Integer updateExperimentReview(ExperimentReview experimentReview) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("experimentReviewId", experimentReview.getExperimentReviewId());
		parameters.addValue("experimentId", experimentReview.getExperimentId());
		parameters.addValue("reviewUserId", experimentReview.getReviewUserId());
		parameters.addValue("comments", experimentReview.getComments());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_REVIEW_QUERY, parameters);
	}
	
	@Override
	public ExperimentReviewDto getExperimentReview(Integer experimentId) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("experimentId", experimentId);
		
		List<ExperimentReviewDto> experimentReviewDtos = namedParameterJdbcTemplate.query(GET_EXPERIMENT_REVIEW_QUERY, parameters, 
				new ExperimentReviewRowMapper());

		if(experimentReviewDtos.isEmpty()) {
			return null;
		}
return experimentReviewDtos.stream().max(Comparator.comparing(ExperimentReviewDto::getExperimentReviewId)).get();
//		return experimentReviewDtos.get(0);
	}
	
	class ExperimentReviewRowMapper implements RowMapper<ExperimentReviewDto> {
		public ExperimentReviewDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ExperimentReviewDto experimentReviewDto = new ExperimentReviewDto();

			experimentReviewDto.setExperimentReviewId(resultSet.getInt("EXP_REVIEW_ID"));
			experimentReviewDto.setExperimentId(resultSet.getInt("EXP_ID"));
			experimentReviewDto.setComments(resultSet.getString("COMMENTS"));
			experimentReviewDto.setReviewUserId(resultSet.getInt("REVIEW_USER_ID"));
			experimentReviewDto.setReviewType(resultSet.getString("REVIEW_TYPE"));
			
			return experimentReviewDto;
		};
	}
	
	class ExperimentExcipientRowMapper implements RowMapper<ExperimentExcipientDto> {
		public ExperimentExcipientDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ExperimentExcipientDto excipientDto = getExperimentExcipientDto(resultSet);
			
			return excipientDto;
		};
	}
		
	class ExperimentRowMapper implements RowMapper<ExperimentDto> {
		public ExperimentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ExperimentDto experimentDto =  getExperimentDto(resultSet);
			ProjectDto project = getProject(resultSet);
			experimentDto.setProject(project);
			return experimentDto;  
		};
	}

	class ExperimentExtractor implements ResultSetExtractor<List<ExperimentDto>> {

		@Override
		public List<ExperimentDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<ExperimentDto> experimentDtoList = new ArrayList<ExperimentDto>();

			while(resultSet.next()) {
				ExperimentDto experimentDto = getExperimentDto(resultSet);

				ExperimentDetailsDto experimentDetails = getExperimentDetailsWithOutContent(resultSet);
//				ExperimentExcipientDto experimentExcipientDto = getExperimentExcipientDto(resultSet);

				if(CollectionUtils.contains(experimentDtoList.iterator(), experimentDto)) {
					int index = experimentDtoList.indexOf(experimentDto);
					experimentDtoList.get(index).getExperimentDetails().add(experimentDetails);
//					experimentDtoList.get(index).getExperimentExcipients().add(experimentExcipientDto);
				} else {

					Set<ExperimentDetailsDto> experimentDetailsList = new LinkedHashSet<>();
//					Set<ExperimentExcipientDto> excipients = new HashSet<>();

					experimentDetailsList.add(experimentDetails);
//					excipients.add(experimentExcipientDto);

					experimentDto.setExperimentDetails(experimentDetailsList);
//					experimentDto.setExperimentExcipients(excipients);

					experimentDtoList.add(experimentDto);
				}

			}
			return experimentDtoList;
		};
	}

	class ExperimentProjectRowMapper implements RowMapper<ExperimentDto> {
		public ExperimentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			ExperimentDto experimentDto = getExperimentDto(resultSet);

			experimentDto.setProject(getProject(resultSet));

			return experimentDto;
		};
	}

	class ExperimentIdRowMapper implements RowMapper<ExperimentDto> {
		public ExperimentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			ExperimentDto experimentDto = getExperimentDto(resultSet);
			experimentDto.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
			experimentDto.setProject(getProject(resultSet));

			Set<ExperimentDetailsDto> experimentDetailsList = new HashSet<>();
			Set<ExperimentExcipientDto> excipients = new HashSet<>();

			ExperimentDetailsDto experimentDetails = getExperimentDetails(resultSet);
			ExperimentExcipientDto experimentExcipientDto = getExperimentExcipientDto(resultSet);

			if(CollectionUtils.isEmpty( experimentDto.getExperimentDetails())) {
				experimentDetailsList.add(experimentDetails);
				experimentDto.setExperimentDetails(experimentDetailsList);
			} else {
				experimentDto.getExperimentDetails().add(experimentDetails);
			}

			if(CollectionUtils.isEmpty( experimentDto.getExperimentExcipients())) {
				excipients.add(experimentExcipientDto);
				experimentDto.setExperimentExcipients(excipients);
			} else {
				experimentDto.getExperimentExcipients().add(experimentExcipientDto);
			}

			return experimentDto;
		};
	}

	private ExperimentDto getExperimentDto(ResultSet resultSet) throws SQLException {

		ExperimentDto experimentDto = new ExperimentDto();
		experimentDto.setExpId(resultSet.getInt("EXP_ID"));
		experimentDto.setExperimentName(resultSet.getString("EXPERIMENT_NAME"));
		experimentDto.setProjectId(resultSet.getInt("PROJECTID"));
		experimentDto.setTeamId(resultSet.getInt("TEAMID"));
		experimentDto.setUserId(resultSet.getInt("USER_ID"));
		experimentDto.setExperimentStatus(resultSet.getString("EXPERIMENT_STATUS"));
		experimentDto.setSummary(resultSet.getString("SUMMARY"));
		experimentDto.setBatchSize(resultSet.getString("BATCH_SIZE"));
		experimentDto.setBatchNumber(resultSet.getString("BATCH_NUMBER"));
		experimentDto.setStatus(resultSet.getString("STATUS"));
		experimentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
		experimentDto.setInsertUser(resultSet.getString("INSERT_USER"));
		experimentDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
		experimentDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
	experimentDto.setAnalysisSubmitDate(resultSet.getDate("ANALYSIS_SUBMIT_DATE"));

		return experimentDto;
	}

	private ProjectDto getProject(ResultSet resultSet) throws SQLException {
		ProjectDto projectDto = new ProjectDto();
		projectDto.setProjectId(resultSet.getInt("PROJECT_ID"));
		projectDto.setProjectName(resultSet.getString("PROJECT_NAME"));
		projectDto.setProductId(resultSet.getInt("PRODUCT_ID"));
		projectDto.setProductName(resultSet.getString("PRODUCT_NAME"));
		projectDto.setProductCode(resultSet.getString("PRODUCT_CODE"));
		projectDto.setStatus(resultSet.getString("STATUS"));
		projectDto.setStrength(resultSet.getString("STRENGTH"));
		projectDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
		projectDto.setDosageName(resultSet.getString("DOSAGE_NAME"));
		projectDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
		projectDto.setFormulationName(resultSet.getString("FORMULATION_NAME"));
		projectDto.setTeamId(resultSet.getInt("TEAM_ID"));
		projectDto.setTeamName(resultSet.getString("TEAM_NAME"));
		projectDto.setMarketId(resultSet.getInt("MARKET_ID"));
		projectDto.setMarkertName(resultSet.getString("MARKET_NAME"));

		return projectDto;
	}

	private ExperimentExcipientDto getExperimentExcipientDto(ResultSet resultSet) throws SQLException {

		ExperimentExcipientDto experimentExcipientDto = new ExperimentExcipientDto();
		experimentExcipientDto.setExcipientId(resultSet.getInt("EXCIPIENT_ID"));
		experimentExcipientDto.setExperimentId(resultSet.getInt("EXP_ID"));
		experimentExcipientDto.setMaterialType(resultSet.getString("MATERIAL_TYPE"));
		experimentExcipientDto.setMaterialName(resultSet.getString("MATERIAL_NAME"));
		experimentExcipientDto.setBatchNo(resultSet.getString("BATCH_NO"));
		experimentExcipientDto.setSourceName(resultSet.getString("SOURCE_NAME"));
		experimentExcipientDto.setPotency(resultSet.getString("POTENCY"));
		experimentExcipientDto.setGrade(resultSet.getString("GRADE"));
		experimentExcipientDto.setStatus(resultSet.getString("STATUS"));

		return experimentExcipientDto;
	}

	private ExperimentDetailsDto getExperimentDetails(ResultSet resultSet) throws SQLException {

		ExperimentDetailsDto experimentDetails = new ExperimentDetailsDto();
		experimentDetails.setExperimentDetailId(resultSet.getInt("EXP_DETAIL_ID"));
		experimentDetails.setExperimentId(resultSet.getInt("EXP_ID"));
		experimentDetails.setName(resultSet.getString("NAME"));
		experimentDetails.setFileContent(resultSet.getString("LOB_DETAILS"));
		experimentDetails.setStatus(resultSet.getString("STATUS"));

		return experimentDetails;

	}

	private ExperimentDetailsDto getExperimentDetailsWithOutContent(ResultSet resultSet) throws SQLException {

		ExperimentDetailsDto experimentDetails = new ExperimentDetailsDto();
		experimentDetails.setExperimentDetailId(resultSet.getInt("EXP_DETAIL_ID"));
		experimentDetails.setExperimentId(resultSet.getInt("EXP_ID"));
		experimentDetails.setName(resultSet.getString("NAME"));
		experimentDetails.setStatus(resultSet.getString("STATUS"));

		return experimentDetails;
	}

	class TestRequestFormRowMapper implements RowMapper<TestRequestFormDto> {
		public TestRequestFormDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			TestRequestFormDto testRequestFormDto = new TestRequestFormDto();
			
			testRequestFormDto.setTestRequestFormId(resultSet.getInt("TRF_ID"));
			testRequestFormDto.setTestRequestFormStatus(resultSet.getString("TRF_STATUS"));
			testRequestFormDto.setExpId(resultSet.getInt("EXP_ID"));
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
			testRequestFormDto.setAnalysisId(resultSet.getInt("ANALYSIS_EXP_ID"));
			
			return testRequestFormDto;
		};
	}
	
}
