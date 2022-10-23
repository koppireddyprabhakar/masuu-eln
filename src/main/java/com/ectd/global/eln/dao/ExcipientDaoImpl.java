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

	@Value(value="${getExcipientById}")
	private String getExcipientByIdQuery;

	@Value(value="${getExcipientList}")
	private String getExcipientListQuery;
	
	@Value(value="${getExcipientsByMaterialName}")
	private String getExcipientsByMaterialNameQuery;

	@Value(value="${createExcipient}")
	private String createExcipientQuery;

	@Value(value="${updateExcipient}")
	private String updateExcipientQuery;

	@Value(value="${deleteExcipient}")
	private String deleteExcipientQuery;

	@Override
	public ExcipientDto getExcipientById(Integer excipientId) {
		List<ExcipientDto> excipients = jdbcTemplate.query(getExcipientByIdQuery + excipientId,
				new ExcipientRowMapper());

		if(excipients.isEmpty()) {
			return null;
		}

		return excipients.get(0);
	}

	@Override
	public List<ExcipientDto> getExcipients() {
		return jdbcTemplate.query(getExcipientListQuery, new ExcipientRowMapper());
	}
	
	@Override
	public List<ExcipientDto> getExcipientsByMaterialName(String materialName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("materialName", materialName);
		return namedParameterJdbcTemplate.query(getExcipientsByMaterialNameQuery, parameters, new ExcipientRowMapper());
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
		parameters.addValue("insertProcess", excipientRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", excipientRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(createExcipientQuery, parameters);
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
		parameters.addValue("updateProcess", excipientRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(updateExcipientQuery, parameters);
	}

	@Override
	public Integer deleteExcipient(Integer excipientId) {
		return jdbcTemplate.update(deleteExcipientQuery, new Object[] {excipientId});
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
			excipientDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			excipientDto.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			excipientDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			excipientDto.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));
			
			return excipientDto;
		};
	}
}
