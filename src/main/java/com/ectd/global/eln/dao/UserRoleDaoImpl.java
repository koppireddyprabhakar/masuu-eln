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

import com.ectd.global.eln.dto.UserRoleDto;
import com.ectd.global.eln.request.UserRoleRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/user-role-dao.properties"})
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getUserRoleById}")
	private String getUserRoleByIdQuery;

	@Value(value="${getUserRoleList}")
	private String getUserRoleListQuery;

	@Value(value="${createUserRole}")
	private String createUserRoleQuery;

	@Value(value="${updateUserRole}")
	private String updateUserRoleQuery;

	@Value(value="${deleteUserRole}")
	private String deleteUserRoleQuery;

	@Override
	public UserRoleDto getUserRoleById(Integer userRoleId) {
		List<UserRoleDto> userRoles = jdbcTemplate.query(getUserRoleByIdQuery + userRoleId,
				new UserRoleMapper());

		if(userRoles.isEmpty()) {
			return null;
		}

		return userRoles.get(0);
	}

	@Override
	public List<UserRoleDto> getUserRoles() {
		return jdbcTemplate.query(getUserRoleListQuery, new UserRoleMapper());
	}

	@Override
	public Integer createUserRole(UserRoleRequest userRoleRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("roleName", userRoleRequest.getRoleName());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("insertUser", "ELN");

		return namedParameterJdbcTemplate.update(createUserRoleQuery, parameters);
	}

	@Override
	public Integer updateUserRole(UserRoleRequest userRoleRequest) {

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("roleId", userRoleRequest.getRoleId());
		parameters.addValue("roleName", userRoleRequest.getRoleName());
		parameters.addValue("status", userRoleRequest.getStatus());

		return namedParameterJdbcTemplate.update(updateUserRoleQuery, parameters);
	}

	@Override
	public Integer deleteUserRole(Integer userRoleId) {
		return jdbcTemplate.update(deleteUserRoleQuery, new Object[] {userRoleId});
	}

	class UserRoleMapper implements RowMapper<UserRoleDto> {
		public UserRoleDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			UserRoleDto userRoleDto = new UserRoleDto();
			userRoleDto.setRoleId(resultSet.getInt("ROLE_ID"));
			userRoleDto.setRoleName(resultSet.getString("ROLE_NAME"));
			userRoleDto.setStatus(resultSet.getString("STATUS"));
			userRoleDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			userRoleDto.setInsertUser(resultSet.getString("INSERT_USER"));
			
			return userRoleDto;
		};
	}	

}
