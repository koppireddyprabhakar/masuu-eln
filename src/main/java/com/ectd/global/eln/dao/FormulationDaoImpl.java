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

import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.FormulationRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/formulation-dao.properties"})
public class FormulationDaoImpl implements FormulationDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getFormulationById}")
	private String getFormulationByIdQuery;

	@Value(value="${getFormulationList}")
	private String getFormulationListQuery;

	@Value(value="${createFormulation}")
	private String createFormulationQuery;

	@Value(value="${updateFormulation}")
	private String updateFormulationQuery;

	@Value(value="${deleteFormulation}")
	private String deleteFormulationQuery;

	@Override
	public FormulationDto getFormulationById(Integer formulationId) {
		List<FormulationDto> formulations = jdbcTemplate.query(getFormulationByIdQuery + formulationId,
				new FormulationRowMapper());

		if(formulations.isEmpty()) {
			return null;
		}

		return formulations.get(0);
	}

	@Override
	public List<FormulationDto> getFormulations() {
		return jdbcTemplate.query(getFormulationListQuery, new FormulationRowMapper());
	}

	@Override
	public Integer createFormulation(FormulationRequest formulationRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("formulationName", formulationRequest.getFormulationName());
		parameters.addValue("insertUser", formulationRequest.getInsertUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", formulationRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("dosageId", formulationRequest.getDosageId());
		parameters.addValue("status", formulationRequest.getStatus());

		return namedParameterJdbcTemplate.update(createFormulationQuery, parameters);
	}

	@Override
	public Integer updateFormulation(FormulationRequest formulationRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("formulationId", formulationRequest.getFormulationId());
		parameters.addValue("formulationName", formulationRequest.getFormulationName());
		parameters.addValue("updateUser", formulationRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		parameters.addValue("dosageId", formulationRequest.getDosageId());
		parameters.addValue("status", formulationRequest.getStatus());

		return namedParameterJdbcTemplate.update(updateFormulationQuery, parameters);
	}

	@Override
	public Integer deleteFormulation(Integer formulationId) {
		return jdbcTemplate.update(deleteFormulationQuery, new Object[] {formulationId});
	}

	class FormulationRowMapper implements RowMapper<FormulationDto> {
		public FormulationDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			FormulationDto formulationDto = new FormulationDto();
			formulationDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
			formulationDto.setFormulationName(resultSet.getString("FORMULATION_NAME"));
			formulationDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			formulationDto.setStatus(resultSet.getString("STATUS"));
			formulationDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			formulationDto.setInsertUser(resultSet.getString("INSERT_USER"));
			formulationDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			formulationDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			
			return formulationDto;
		};
	}
}
