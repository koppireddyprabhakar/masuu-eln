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

import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.request.ExperimentAttachment;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value={"classpath:sql/experiment-dao.properties"})
public class ExperimentAttachmentDaoImpl implements ExperimentAttachmentDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${get.experiment.attachment.by.id}")
	private String GET_EXPERIMENT_ATTACHMENT_BY_ID;
	
	@Value("${get.experiment.attachment.list}")
	private String GET_EXPERIMENT_ATTACHMENT_LIST;
	
	@Value("${create.experiment.attachment}")
	private String CREATE_EXPERIMENT_ATTACHMENT;
	
	@Value("${update.experiment.attachment}")
	private String UPDATE_EXPERIMENT_ATTACHMENT;

	@Override
	public ExperimentAttachmentDto getExperimentAttachmentById(Integer experimentAttachmentId) {
		List<ExperimentAttachmentDto> attachments = jdbcTemplate.query(GET_EXPERIMENT_ATTACHMENT_BY_ID + experimentAttachmentId, new ExperimentAttachmentRowMapper());
		
		if(CollectionUtils.isEmpty(attachments)) {
			return null;
		}
		
		return attachments.get(0);
	}

	@Override
	public List<ExperimentAttachmentDto> getExperimentAttachments(Integer experimentId, String fileName) {
		StringBuilder sb = new StringBuilder(GET_EXPERIMENT_ATTACHMENT_LIST);
		
		if(experimentId != null) {
			sb.append(" AND EXP_ID = " + experimentId);
		}
		if(fileName != null) {
			sb.append(" AND ATTACHMENT_LOCATION LIKE '%" + fileName +"%'");
		}
		
		sb.append(" AND STATUS = '" + ElnUtils.STATUS.ACTIVE.getValue()+"'");
		
		return jdbcTemplate.query(sb.toString(), new ExperimentAttachmentRowMapper());
	}

	@Override
	public Integer createExperimentAttachment(ExperimentAttachment experimentAttachment) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("experimentId", experimentAttachment.getExperimentId());
		parameters.addValue("attachmentLocation", experimentAttachment.getAttachmentLocation());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(CREATE_EXPERIMENT_ATTACHMENT, parameters);
	}

	@Override
	public Integer updateExperimentAttachment(ExperimentAttachment experimentAttachment) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		parameters.addValue("experimentAttachmentId", experimentAttachment.getExperimentAttachmentId());
		parameters.addValue("experimentId", experimentAttachment.getExperimentId());
		parameters.addValue("attachmentLocation", experimentAttachment.getAttachmentLocation());
		parameters.addValue("status", experimentAttachment.getStatus());
		parameters.addValue("insertUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", ElnUtils.DEFAULT_USER_ID);
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(UPDATE_EXPERIMENT_ATTACHMENT, parameters);
	}

	@Override
	public Integer deleteExperimentAttachment(ExperimentAttachment experimentAttachment) {
		if(experimentAttachment.getStatus() == null) {
			experimentAttachment.setStatus(ElnUtils.STATUS.INACTIVE.getValue());
		}
		return this.updateExperimentAttachment(experimentAttachment);
	}
	
	class ExperimentAttachmentRowMapper implements RowMapper<ExperimentAttachmentDto> {
		public ExperimentAttachmentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

			ExperimentAttachmentDto experimentAttachmentDto = new ExperimentAttachmentDto();
			experimentAttachmentDto.setExperimentAttachmentId(resultSet.getInt("EXP_ATT_ID"));
			experimentAttachmentDto.setExperimentId(resultSet.getInt("EXP_ID"));
			experimentAttachmentDto.setAttachmentLocation(resultSet.getString("ATTACHMENT_LOCATION"));
			experimentAttachmentDto.setStatus(resultSet.getString("STATUS"));
			experimentAttachmentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			experimentAttachmentDto.setInsertUser(resultSet.getString("INSERT_USER"));
			experimentAttachmentDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			experimentAttachmentDto.setUpdateUser(resultSet.getString("UPDATE_USER"));

			return experimentAttachmentDto;
		};
	}

}
