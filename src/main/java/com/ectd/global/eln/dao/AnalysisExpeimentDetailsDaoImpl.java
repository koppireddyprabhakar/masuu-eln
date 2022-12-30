package com.ectd.global.eln.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.AnalysisDetailsDto;
import com.ectd.global.eln.request.AnalysisDetails;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value={"classpath:sql/analysis-dao.properties"})
public class AnalysisExpeimentDetailsDaoImpl implements AnalysisExpeimentDetailsDao {
	
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get.analysis.details.by.id}")
	private String GET_ANALYSIS_DETAILS_BY_ID_QUERY;
	
	@Value("${get.analysis.details.list}")
	private String GET_ANALYSIS_DETAILS_LIST_QUERY;
	
	@Value(value="${create.analysis.details}")
	private String CREATE_ANALYSIS_DETAILS_QUERY;
	
	@Value(value="${update.analysis.details}")
	private String UPDATE_ANALYSIS_DETAILS_QUERY;

	@Override
	public AnalysisDetailsDto getAnalysisDetailsById(Integer analysisDetailId) {
		return jdbcTemplate.queryForObject(GET_ANALYSIS_DETAILS_BY_ID_QUERY + analysisDetailId, 
				new BeanPropertyRowMapper<AnalysisDetailsDto>());
	}

	@Override
	public List<AnalysisDetailsDto> getAnalysisDetails() {
		return jdbcTemplate.query(GET_ANALYSIS_DETAILS_LIST_QUERY, new BeanPropertyRowMapper<AnalysisDetailsDto>());
	}

	@Override
	public Integer createAnalysisDetails(AnalysisDetails analysisDetails) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		KeyHolder keyHolder = new GeneratedKeyHolder();

		parameters.addValue("analysisId", analysisDetails.getAnalysisId());
		parameters.addValue("fileContent", analysisDetails.getFileContent().getBytes());
		parameters.addValue("name", analysisDetails.getName());
		parameters.addValue("status", analysisDetails.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(CREATE_ANALYSIS_DETAILS_QUERY, parameters, keyHolder);
	}

	@Override
	public Integer updateAnalysisDetails(AnalysisDetails analysisDetails) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("experimentDetailId", analysisDetails.getAnalysisDetailId());
		parameters.addValue("experimentId", analysisDetails.getAnalysisId());
		parameters.addValue("fileContent", analysisDetails.getFileContent().getBytes());
		parameters.addValue("name", analysisDetails.getName());
		parameters.addValue("status", analysisDetails.getStatus());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_DETAILS_QUERY, parameters);
	}

	@Override
	public Integer deleteAnalysisDetails(AnalysisDetails analysisDetails) {
		return this.updateAnalysisDetails(analysisDetails);
	}

}
