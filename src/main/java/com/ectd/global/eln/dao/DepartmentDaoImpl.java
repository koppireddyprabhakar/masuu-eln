package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.DepartmentDto;
import com.ectd.global.eln.dto.TeamsDto;
import com.ectd.global.eln.request.DepartmentRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/department-dao.properties"})
public class DepartmentDaoImpl implements DepartmentDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getDepartment}")
	private String getDepartmentQuery;

	@Value(value="${getDepartmentList}")
	private String getDepartmentListQuery;

	@Value(value="${createDepartment}")
	private String createDepartmentQuery;

	@Value(value="${updateDepartment}")
	private String updateDepartmentQuery;

	@Value(value="${deleteDepartment}")
	private String deleteDepartmentQuery;
	
	@Value(value="${getDepartmentsWithTeams}")
	private String getDepartmentsWithTeamsQuery;

	@Override
	public DepartmentDto getDepartmentById(Integer departmentId) {
		List<DepartmentDto> departments = jdbcTemplate.query(getDepartmentQuery + departmentId,
				new DepartmentRowMapper());

		if(departments.isEmpty()) {
			return null;
		}

		return departments.get(0);
	}

	@Override
	public List<DepartmentDto> getDepartments() {
		return jdbcTemplate.query(getDepartmentListQuery, new DepartmentRowMapper());
	}

	@Override
	public Integer createDepartment(DepartmentRequest departmentRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("departmentName", departmentRequest.getDepartmentName());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertUser", "ELN");
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(createDepartmentQuery, parameters);
	}

	@Override
	public Integer updateDepartment(DepartmentRequest departmentRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("deptId", departmentRequest.getDeptId());
		parameters.addValue("departmentName", departmentRequest.getDepartmentName());
		parameters.addValue("status", departmentRequest.getStatus());
		return namedParameterJdbcTemplate.update(updateDepartmentQuery, parameters);
	}

	@Override
	public Integer deleteDepartment(Integer departmentId) {
		return jdbcTemplate.update(deleteDepartmentQuery, new Object[] {departmentId});
	}

	@Override
	public List<DepartmentDto> getDepartmentsWithTeams() {
		return jdbcTemplate.query(getDepartmentsWithTeamsQuery, new DepartmentTeamsExtractor());
	}
	
	class DepartmentRowMapper implements RowMapper<DepartmentDto> {
		public DepartmentDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setDeptId(resultSet.getInt("DEPT_ID"));
			departmentDto.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
			departmentDto.setStatus(resultSet.getString("STATUS"));
			departmentDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			departmentDto.setInsertUser(resultSet.getString("INSERT_USER"));
			return  departmentDto;
		};
	}
	
	class DepartmentTeamsExtractor implements ResultSetExtractor<List<DepartmentDto>> {
		
		@Override
		public List<DepartmentDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<DepartmentDto> departmentDtos = new ArrayList<>();

			while(resultSet.next()) {
				DepartmentDto departmentDto = new DepartmentDto();
				departmentDto.setDeptId(resultSet.getInt("DEPT_ID"));;
				departmentDto.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
				
				TeamsDto teamsDto = new TeamsDto();
				teamsDto.setTeamId(resultSet.getInt("TEAM_ID"));
				teamsDto.setTeamName(resultSet.getString("TEAM_NAME"));
				
				if(CollectionUtils.contains(departmentDtos.iterator(), departmentDto)) {
					int index = departmentDtos.indexOf(departmentDto);
					departmentDtos.get(index).getTeamsDtos().add(teamsDto);
				} else {
					
					List<TeamsDto> teamsDtos = new ArrayList<TeamsDto>();
					teamsDtos.add(teamsDto);
					departmentDto.setTeamsDtos(teamsDtos);

					departmentDtos.add(departmentDto);
				}
			}
			return departmentDtos;
		};
	}
	
}
