package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
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

import com.ectd.global.eln.dto.DosageDto;
import com.ectd.global.eln.dto.FormulationDto;
import com.ectd.global.eln.request.DosageRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/dosage-dao.properties"})
public class DosageDaoImpl implements DosageDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getDosageById}")
	private String getDosageByIdQuery;

	@Value(value="${getDosageList}")
	private String getDosageListQuery;

	@Value(value="${createDosage}")
	private String createDosageQuery;

	@Value(value="${updateDosage}")
	private String updateDosageQuery;

	@Value(value="${deleteDosage}")
	private String deleteDosageQuery;

	@Value(value="${getDosagesAndFormulations}")
	private String getDosagesAndFormulationsQuery;

	@Value(value="${createFormulation}")
	private String createFormulationQuery;
	
	@Value(value="${updateFormulation}")
	private String updateFormulationQuery;

	@Override
	public DosageDto getDosageById(Integer dosageId) {
		List<DosageDto> dosages = jdbcTemplate.query(getDosageByIdQuery + dosageId,
				new DosageRowMapper());

		if(dosages.isEmpty()) {
			return null;
		}

		return dosages.get(0);
	}

	@Override
	public List<DosageDto> getDosages() {
		return jdbcTemplate.query(getDosageListQuery, new DosageRowMapper());
	}

	@Override
	public Integer createDosage(DosageRequest dosageRequest) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("dosageName", dosageRequest.getDosageName());
		parameters.addValue("insertProcess", dosageRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", dosageRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(createDosageQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	@Override
	public Integer updateDosage(DosageRequest dosageRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("dosageId", dosageRequest.getDosageId());
		parameters.addValue("dosageName", dosageRequest.getDosageName());
		parameters.addValue("updateProcess", dosageRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateDosageQuery, parameters);
	}

	@Override
	public Integer deleteDosage(Integer dosageId) {
		return jdbcTemplate.update(deleteDosageQuery, new Object[] {dosageId});
	}

	@Override
	public List<DosageDto> getDosagesAndFormulations() {
		List<DosageDto> dtos = jdbcTemplate.query(getDosagesAndFormulationsQuery, new DosageFormulationExtractor());
		return dtos;
	}
	
	@Override
	public Boolean saveDosageWithFormulations(DosageRequest dosageRequest) {
		Integer dosageId = this.createDosage(dosageRequest);
		int[] insertedRows = this.batchInsert(dosageRequest.getFormulations(), dosageId);

		if(insertedRows.length > 0) {
			return true;
		}

		return false;
	}

	private int[] batchInsert(List<FormulationDto> formulationDtos, Integer dosageId) {
		
		formulationDtos.forEach(f -> 
		{	
			f.setDosageId(dosageId);
			f.setInsertProcess("ELN");
			f.setUpdateProcess("ELN");
			f.setInsertDate(ElnUtils.getTimeStamp());
			f.setUpdateDate(ElnUtils.getTimeStamp());
		});
		
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(formulationDtos.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( createFormulationQuery, batch);
	}
	
	@Override
	public Boolean updateDosageWithFormulations(DosageRequest dosageRequest) {
		this.updateDosage(dosageRequest);
		
		List<FormulationDto> updateFormulations = dosageRequest.getFormulations().stream().filter(f -> !(ObjectUtils.isEmpty(f.getFormulationId())) ).collect(Collectors.toList());
		List<FormulationDto> insertFormulations = dosageRequest.getFormulations().stream().filter(f -> ObjectUtils.isEmpty(f.getFormulationId())).collect(Collectors.toList());
		
		int[] updatedRows = null;
		if(!CollectionUtils.isEmpty(updateFormulations)) {
			updatedRows = this.batchUpdate(updateFormulations);
		}
		
		if(!CollectionUtils.isEmpty(insertFormulations)) {
			this.batchInsert(insertFormulations, dosageRequest.getDosageId());
		}
		
		if(updatedRows != null && updatedRows.length > 0) {
			return true;
		}

		return false;
	}

	private int[] batchUpdate(List<FormulationDto> formulationDtos) {
		
		formulationDtos.forEach(f -> {
			f.setUpdateProcess("ELN");
			f.setUpdateDate(ElnUtils.getTimeStamp());
		});
		
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(formulationDtos.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( updateFormulationQuery, batch);
	}

	class DosageRowMapper implements RowMapper<DosageDto> {
		public DosageDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DosageDto dosageDto = new DosageDto();
			dosageDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
			dosageDto.setDosageName(resultSet.getString("DOSAGE_NAME"));

			return dosageDto;
		};
	}

	class DosageFormulationExtractor implements ResultSetExtractor<List<DosageDto>> {

		@Override
		public List<DosageDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<DosageDto> dosageDtos = new ArrayList<>();

			while(resultSet.next()) {

				DosageDto dosageDto = new DosageDto();
				dosageDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
				dosageDto.setDosageName(resultSet.getString("DOSAGE_NAME"));
				
				FormulationDto formulationDto = new FormulationDto();
				formulationDto.setFormulationId(resultSet.getInt("FORMULATION_ID"));
				formulationDto.setFormulationName(resultSet.getString("FORMULATION_NAME"));
				formulationDto.setDosageId(resultSet.getInt("DOSAGE_ID"));
				
				if(CollectionUtils.contains(dosageDtos.iterator(), dosageDto)) {
					int index = dosageDtos.indexOf(dosageDto);
					dosageDtos.get(index).getFormulations().add(formulationDto);
				} else {

					List<FormulationDto> formulationDtos = new ArrayList<FormulationDto>();
					formulationDtos.add(formulationDto);
					dosageDto.setFormulations(formulationDtos);

					dosageDtos.add(dosageDto);
				}
			}
			return dosageDtos;
		};
	}

}
