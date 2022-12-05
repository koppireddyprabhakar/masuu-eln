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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.ExperimentDetailsDto;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value={"classpath:sql/experiment-dao.properties"})
public class ExperimentDetailsDaoImpl implements ExperimentDetailsDao{

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get.experiment.details.by.id}")
	private String GET_EXPERIMENT_DETAILS_BY_ID_QUERY;
	
	@Value("${get.experiment.details.list}")
	private String GET_EXPERIMENT_DETAILS_LIST_QUERY;
	
	@Value(value="${create.experiment.details}")
	private String CREATE_EXPERIMENT_DETAILS_QUERY;
	
	@Value(value="${update.experiment.details}")
	private String UPDATE_EXPERIMENT_DETAILS_QUERY;
	
	@Override
	public ExperimentDetailsDto getExperimentDetailsById(Integer experimentDetailId) {
		
		List<ExperimentDetailsDto> experimentDetailsDtos = jdbcTemplate.query(GET_EXPERIMENT_DETAILS_BY_ID_QUERY + experimentDetailId, 
				new ExperimentDetailsRowMapper());
		
		if(CollectionUtils.isEmpty(experimentDetailsDtos)) {
			return null;
		}
		
		return experimentDetailsDtos.get(0);
	}

	@Override
	public List<ExperimentDetailsDto> getExperimentDetails() {
		return jdbcTemplate.query(GET_EXPERIMENT_DETAILS_LIST_QUERY, new ExperimentDetailsRowMapper());
	}

	@Override
	public Integer createExperimentDetails(ExperimentDetails experimentDetails) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();
//		parameters.addValue("experimentDetailId", experimentDetails.getExperimentDetailId());
		parameters.addValue("experimentId", experimentDetails.getExperimentId());
		parameters.addValue("fileContent", experimentDetails.getFileContent());
		parameters.addValue("name", experimentDetails.getName());
		parameters.addValue("status", experimentDetails.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(CREATE_EXPERIMENT_DETAILS_QUERY, parameters, keyHolder);
	}

	@Override
	public Integer updateExperimentDetails(ExperimentDetails experimentDetails) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("experimentDetailId", experimentDetails.getExperimentDetailId());
		parameters.addValue("experimentId", experimentDetails.getExperimentId());
		parameters.addValue("fileContent", experimentDetails.getFileContent());
		parameters.addValue("name", experimentDetails.getName());
		parameters.addValue("status", experimentDetails.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_DETAILS_QUERY, parameters);
	}

	@Override
	public Integer deleteExperimentDetails(ExperimentDetails experimentDetails) {
		return this.updateExperimentDetails(experimentDetails);
	}
	
	class ExperimentDetailsRowMapper implements RowMapper<ExperimentDetailsDto> {

		@Override
		public ExperimentDetailsDto mapRow(ResultSet rs, int rowNum) throws SQLException {

			ExperimentDetailsDto experimentDetailsDto = new ExperimentDetailsDto();
			experimentDetailsDto.setExperimentDetailId(rs.getInt("EXP_DETAIL_ID"));
			experimentDetailsDto.setExperimentId(rs.getInt("EXP_ID"));
			experimentDetailsDto.setName(rs.getString("NAME"));
			experimentDetailsDto.setFileContent(rs.getString("LOB_DETAILS"));
			experimentDetailsDto.setStatus(rs.getString("STATUS"));
			experimentDetailsDto.setInsertUser(rs.getString("INSERT_USER"));
			experimentDetailsDto.setInsertDate(rs.getDate("INSERT_DATE"));
			experimentDetailsDto.setUpdateUser(rs.getString("UPDATE_USER"));
			experimentDetailsDto.setUpdateDate(rs.getDate("UPDATE_DATE"));
			
			return experimentDetailsDto;
		}
		
	}

}
