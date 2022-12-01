package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.ectd.global.eln.dto.ProjectDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;
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

	@Override
	public ExperimentDto getExperimentById(Integer experimentId) {
		List<ExperimentDto> experiments = jdbcTemplate.query(GET_EXPERIMENT_BY_ID_QUERY + experimentId,
				new ExperimentRowMapper());

		if(experiments.isEmpty()) {
			return null;
		}

		return experiments.get(0);
	}

	@Override
	public List<ExperimentDto> getExperiments(Integer userId) {

		StringBuilder sb = new StringBuilder(GET_EXPERIMENT_LIST_QUERY);

		if(userId != null) {
			sb.append(" AND USER_ID = ").append(userId);
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
		parameters.addValue("experimentStatus", experimentRequest.getExperimentStatus());
		parameters.addValue("summary", experimentRequest.getSummary());
		parameters.addValue("status", experimentRequest.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
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

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(experimentDetailsList.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_EXPERIMENT_DETAILS_QUERY, batch );
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
		return this.namedParameterJdbcTemplate.batchUpdate(CREATE_EXPERIMENT_EXCIPIENT_QUERY, batch);
	}

	@Override
	public int[] batchExcipientUpdate(List<ExcipientRequest> excipients) {

		excipients.stream().forEach(e -> {
			e.setUpdateDate(ElnUtils.getTimeStamp());
			e.setUpdateUser(ElnUtils.DEFAULT_USER_ID);
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(excipients.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate(UPDATE_EXPERIMENT_EXCIPIENT_QUERY, batch);
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
		parameters.addValue("status", experimentRequest.getStatus());
		parameters.addValue("updateUser", experimentRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_QUERY, parameters);
	}

	@Override
	public Integer deleteExperiment(Integer experimentId) {
		return jdbcTemplate.update(DELETE_EXPERIMENT_QUERY, new Object[] {experimentId});
	}

	class ExperimentRowMapper implements RowMapper<ExperimentDto> {
		public ExperimentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return getExperimentDto(resultSet);
		};
	}

	@Override
	public List<ExperimentDto> getExperimentsWithProject() {
		return jdbcTemplate.query(GET_EXPERIMENT_PROJECT_QUERY, new ExperimentProjectRowMapper());
	}

	@Override
	public List<ExperimentDto> getExperimentsInfo(Integer experimentId) {
		
		return jdbcTemplate.query(GET_EXPERIMENT_INFO_QUERY + experimentId, new ExperimentIdRowMapper());
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
			experimentDto.setProject(getProject(resultSet));
			
			List<ExperimentDetails> experimentDetailsList = new ArrayList<>();
		    List<ExperimentExcipientDto> excipients = new ArrayList<>();
			
		    ExperimentDetails experimentDetails = getExperimentDetails(resultSet);
		    ExperimentExcipientDto experimentExcipientDto = getExperimentExcipientDto(resultSet);
		    
		    if(CollectionUtils.isEmpty( experimentDto.getExperimentDetailsList())) {
		    	experimentDetailsList.add(experimentDetails);
		    	experimentDto.setExperimentDetailsList(experimentDetailsList);
		    } else {
		    	experimentDto.getExperimentDetailsList().add(experimentDetails);
		    }
		    
		    if(CollectionUtils.isEmpty( experimentDto.getExperimentExcipientList())) {
		    	excipients.add(experimentExcipientDto);
		    	experimentDto.setExperimentExcipientList(excipients);
		    } else {
		    	experimentDto.getExperimentExcipientList().add(experimentExcipientDto);
		    }
		    
			return experimentDto;
		};
	}
	
	private ExperimentDto getExperimentDto(ResultSet resultSet) throws SQLException {
		
		ExperimentDto experimentDto = new ExperimentDto();
		experimentDto.setExpId(resultSet.getInt("EXP_ID"));
		experimentDto.setExperimentName(resultSet.getString("EXPERIMENT_NAME"));
		experimentDto.setProjectId(resultSet.getInt("PROJECT_ID"));
		experimentDto.setTeamId(resultSet.getInt("TEAM_ID"));
		experimentDto.setUserId(resultSet.getInt("USER_ID"));
		experimentDto.setExperimentStatus(resultSet.getString("EXPERIMENT_STATUS"));
		experimentDto.setSummary(resultSet.getString("SUMMARY"));
		experimentDto.setStatus(resultSet.getString("STATUS"));
		experimentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
		experimentDto.setInsertUser(resultSet.getString("INSERT_USER"));
		experimentDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
		experimentDto.setUpdateUser(resultSet.getString("UPDATE_USER"));

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
//		experimentExcipientDto.setExcipientsName(resultSet.getString("EXCIPIENTS_NAME"));
		experimentExcipientDto.setMaterialType(resultSet.getString("MATERIAL_TYPE"));
		experimentExcipientDto.setMaterialName(resultSet.getString("MATERIAL_NAME"));
		experimentExcipientDto.setBatchNo(resultSet.getString("BATCH_NO"));
		experimentExcipientDto.setSourceName(resultSet.getString("SOURCE_NAME"));
		experimentExcipientDto.setPotency(resultSet.getString("POTENCY"));
		experimentExcipientDto.setGrade(resultSet.getString("GRADE"));
		experimentExcipientDto.setStatus(resultSet.getString("STATUS"));
		
		return experimentExcipientDto;
	}
	
	private ExperimentDetails getExperimentDetails(ResultSet resultSet) throws SQLException {
		
		ExperimentDetails experimentDetails = new ExperimentDetails();
		experimentDetails.setExperimentDetailId(resultSet.getInt("EXP_DETAIL_ID"));
		experimentDetails.setExperimentId(resultSet.getInt("EXP_ID"));
		experimentDetails.setName(resultSet.getString("NAME"));
		experimentDetails.setFileContent(resultSet.getBytes("LOB_DETAILS"));
		experimentDetails.setStatus(resultSet.getString("STATUS"));
		
		return experimentDetails;
		
	}
	
}
