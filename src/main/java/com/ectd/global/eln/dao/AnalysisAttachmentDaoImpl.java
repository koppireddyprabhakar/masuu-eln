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
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.AnalysisAttachmentDto;
import com.ectd.global.eln.request.AnalysisAttachment;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value={"classpath:sql/analysis-dao.properties"})
public class AnalysisAttachmentDaoImpl implements AnalysisAttachmentDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get.analysis.attachment.by.id}")
	private String GET_ANALYSIS_ATTACHMENT_BY_ID;
	
	@Value("${get.analysis.attachment.list}")
	private String GET_ANALYSIS_ATTACHMENT_LIST;
	
	@Value("${create.analysis.attachment}")
	private String CREATE_ANALYSIS_ATTACHMENT;
	
	@Value("${update.analysis.attachment}")
	private String UPDATE_ANALYSIS_ATTACHMENT;

	@Override
	public AnalysisAttachmentDto getAnalysisAttachmentById(Integer analysisExperimentId) {
List<AnalysisAttachmentDto> attachments = jdbcTemplate.query(GET_ANALYSIS_ATTACHMENT_BY_ID + analysisExperimentId, new AnalysisAttachmentRowMapper());
		
		if(CollectionUtils.isEmpty(attachments)) {
			return null;
		}
		
		return attachments.get(0);
	}

	@Override
	public List<AnalysisAttachmentDto> getAnalysisAttachments(Integer experimentId, String fileName) {
		StringBuilder sb = new StringBuilder(GET_ANALYSIS_ATTACHMENT_LIST);

		if(experimentId != null) {
			sb.append(" AND ANALYSIS_EXP_ID = " + experimentId);
		}
		if(fileName != null) {
			sb.append(" AND ATTACHMENT_LOCATION LIKE '%" + fileName +"%'");
		}
		
		sb.append(" AND STATUS = '" + ElnUtils.STATUS.ACTIVE.getValue()+"'");

		return jdbcTemplate.query(sb.toString(), new AnalysisAttachmentRowMapper());
	}

	@Override
	public Integer createAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("experimentId", analysisAttachment.getExperimentId());
		parameters.addValue("attachmentLocation", analysisAttachment.getAttachmentLocation());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("fromSummary", analysisAttachment.getFromSummary());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(CREATE_ANALYSIS_ATTACHMENT, parameters);

	}

	@Override
	public Integer updateAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("analysisAttachmentId", analysisAttachment.getAnalysisAttachmentId());
		parameters.addValue("experimentId", analysisAttachment.getExperimentId());
		parameters.addValue("attachmentLocation", analysisAttachment.getAttachmentLocation());
		parameters.addValue("status", analysisAttachment.getStatus());
		parameters.addValue("fromSummary", analysisAttachment.getFromSummary());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_ANALYSIS_ATTACHMENT, parameters);
	}

	@Override
	public Integer deleteAnalysisAttachment(AnalysisAttachment analysisAttachment) {
		if(analysisAttachment.getStatus() == null) {
			analysisAttachment.setStatus(ElnUtils.STATUS.INACTIVE.getValue());
		}
		return this.updateAnalysisAttachment(analysisAttachment);
	}
	
	class AnalysisAttachmentRowMapper implements RowMapper<AnalysisAttachmentDto> {
		
		public AnalysisAttachmentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			AnalysisAttachmentDto analysisAttachmentDto = new AnalysisAttachmentDto();
			analysisAttachmentDto.setAnalysisAttachmentId(resultSet.getInt("ANALYSIS_ATT_ID"));
			analysisAttachmentDto.setAnalysisExperimentId(resultSet.getInt("ANALYSIS_EXP_ID"));
			analysisAttachmentDto.setAttachmentLocation(resultSet.getString("ATTACHMENT_LOCATION"));
			analysisAttachmentDto.setStatus(resultSet.getString("STATUS"));
			analysisAttachmentDto.setFromSummary(resultSet.getString("FROM_SUMMARY"));
			analysisAttachmentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			analysisAttachmentDto.setInsertUser(resultSet.getString("INSERT_USER"));
			analysisAttachmentDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			analysisAttachmentDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			
			return analysisAttachmentDto;
		};
	}

}
