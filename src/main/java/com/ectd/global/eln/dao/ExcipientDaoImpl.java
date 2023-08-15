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

import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/excipient-dao.properties"})
public class ExcipientDaoImpl implements ExcipientDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${get.excipient.by.id}")
	private String GET_EXCIPIENT_BY_ID_QUERY;

	@Value(value="${get.excipient.list}")
	private String GET_EXCIPIENT_LIST_QUERY;
	
	@Value(value="${get.excipients.by.material.name}")
	private String GET_EXCIPIENTS_BY_MATERIAL_NAME_QUERY;

	@Value(value="${create.excipient}")
	private String CREATE_EXCIPIENT_QUERY;

	@Value(value="${update.excipient}")
	private String UPDATE_EXCIPIENT_QUERY;

	@Value(value="${delete.excipient}")
	private String DELETE_EXCIPIENT_QUERY;

	@Override
	public ExcipientDto getExcipientById(Integer excipientId) {
		List<ExcipientDto> excipients = jdbcTemplate.query(GET_EXCIPIENT_BY_ID_QUERY + excipientId,
				new ExcipientRowMapper());

		if(excipients.isEmpty()) {
			return null;
		}

		return excipients.get(0);
	}

	@Override
	public List<ExcipientDto> getExcipients(String creationSource) {

		StringBuilder sb = new StringBuilder(GET_EXCIPIENT_LIST_QUERY);

		if(creationSource != null) {
			sb.append(" AND CREATION_SOURCE = '" + creationSource+"'");
		}
		
		sb.append(" ORDER BY INSERT_DATE DESC");

		return jdbcTemplate.query(sb.toString(), new ExcipientRowMapper());
	}
	
	@Override
	public List<ExcipientDto> getExcipientsByMaterialName(String materialName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("materialName", materialName);
		return namedParameterJdbcTemplate.query(GET_EXCIPIENTS_BY_MATERIAL_NAME_QUERY, parameters, new ExcipientRowMapper());
	}

	@Override
	public Integer createExcipient(ExcipientRequest excipientRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("excipientsName", excipientRequest.getExcipientsName());
		parameters.addValue("materialType", excipientRequest.getMaterialType());
		parameters.addValue("materialName", excipientRequest.getMaterialName());
		parameters.addValue("batchNo", excipientRequest.getBatchNo());
		parameters.addValue("sourceName", excipientRequest.getSourceName());
		parameters.addValue("potency", excipientRequest.getPotency());
		parameters.addValue("grade", excipientRequest.getGrade());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("creationSource", excipientRequest.getCreationSource());
		parameters.addValue("insertUser", excipientRequest.getInsertUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", "ELN");
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(CREATE_EXCIPIENT_QUERY, parameters);
	}

	@Override
	public Integer updateExcipient(ExcipientRequest excipientRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("excipientId", excipientRequest.getExcipientId());
		parameters.addValue("excipientsName", excipientRequest.getExcipientsName());
		parameters.addValue("materialType", excipientRequest.getMaterialType());
		parameters.addValue("materialName", excipientRequest.getMaterialName());
		parameters.addValue("batchNo", excipientRequest.getBatchNo());
		parameters.addValue("sourceName", excipientRequest.getSourceName());
		parameters.addValue("potency", excipientRequest.getPotency());
		parameters.addValue("grade", excipientRequest.getGrade());
		parameters.addValue("status", excipientRequest.getStatus());
		parameters.addValue("creationSource", excipientRequest.getCreationSource());
		parameters.addValue("updateUser", excipientRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(UPDATE_EXCIPIENT_QUERY, parameters);
	}

	@Override
	public Integer deleteExcipient(Integer excipientId) {
		return jdbcTemplate.update(DELETE_EXCIPIENT_QUERY, new Object[] {excipientId});
	}
	
	class ExcipientRowMapper implements RowMapper<ExcipientDto> {
		public ExcipientDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			ExcipientDto excipientDto = new ExcipientDto();
			excipientDto.setExcipientId(resultSet.getInt("EXCIPIENT_ID"));
			excipientDto.setExcipientsName(resultSet.getString("EXCIPIENTS_NAME"));
			excipientDto.setMaterialType(resultSet.getString("MATERIAL_TYPE"));
			excipientDto.setMaterialName(resultSet.getString("MATERIAL_NAME"));
			excipientDto.setBatchNo(resultSet.getString("BATCH_NO"));
			excipientDto.setSourceName(resultSet.getString("SOURCE_NAME"));
			excipientDto.setPotency(resultSet.getString("POTENCY"));
			excipientDto.setGrade(resultSet.getString("GRADE"));
			excipientDto.setStatus(resultSet.getString("STATUS"));
			excipientDto.setCreationSource(resultSet.getString("CREATION_SOURCE"));
			excipientDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			excipientDto.setInsertUser(resultSet.getString("INSERT_USER"));
			excipientDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			excipientDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			
			return excipientDto;
		};
	}
}
