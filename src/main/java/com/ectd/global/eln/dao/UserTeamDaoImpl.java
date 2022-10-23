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

import com.ectd.global.eln.dto.UserTeamDto;
import com.ectd.global.eln.request.UserTeamRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/user-role-dao.properties"})
public class UserTeamDaoImpl implements UserTeamDao {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getUserTeamById}")
	private String getUserTeamByIdQuery;

	@Value(value="${getUserTeamList}")
	private String getUserTeamListQuery;

	@Value(value="${createUserTeam}")
	private String createUserTeamQuery;

	@Value(value="${updateUserTeam}")
	private String updateUserTeamQuery;

	@Value(value="${deleteUserTeam}")
	private String deleteUserTeamQuery;
	
	@Override
	public UserTeamDto getUserTeamByCompositeId(UserTeamRequest userTeamRequest) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", userTeamRequest.getTeamId());
		parameters.addValue("userId", userTeamRequest.getUserId());
		
		List<UserTeamDto> userTeamList = namedParameterJdbcTemplate.query(getUserTeamByIdQuery, parameters, 
				new UserTeamRowMapper());

		if(userTeamList.isEmpty()) {
			return null;
		}

		return userTeamList.get(0);

	}

	@Override
	public List<UserTeamDto> getUserTeamList() {
		return jdbcTemplate.query(getUserTeamListQuery, new UserTeamRowMapper());
	}

	@Override
	public Integer createUserTeam(UserTeamRequest userTeamRequest) {
	
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", userTeamRequest.getTeamId());
		parameters.addValue("userId", userTeamRequest.getUserId());
		parameters.addValue("insertProcess", userTeamRequest.getInsertProcess());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateProcess", userTeamRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(createUserTeamQuery, parameters);
	}

	@Override
	public Integer updateUserTeam(UserTeamRequest userTeamRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("teamId", userTeamRequest.getTeamId());
		parameters.addValue("userId", userTeamRequest.getUserId());
		parameters.addValue("updateProcess", userTeamRequest.getUpdateProcess());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		
		return namedParameterJdbcTemplate.update(updateUserTeamQuery, parameters);
	}

	@Override
	public Integer deleteUserTeam(Integer userTeamId) {
		return jdbcTemplate.update(deleteUserTeamQuery, new Object[] {userTeamId});
	}
	
	class UserTeamRowMapper implements RowMapper<UserTeamDto> {
		
		public UserTeamDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			UserTeamDto userTeam = new UserTeamDto();
			userTeam.setTeamId(resultSet.getInt("TEAM_ID"));
			userTeam.setUserId(resultSet.getInt("USER_ID"));
			userTeam.setInsertDate(resultSet.getDate("INSERT_DATE"));
			userTeam.setInsertProcess(resultSet.getString("INSERT_PROCESS"));
			userTeam.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			userTeam.setUpdateProcess(resultSet.getString("UPDATE_PROCESS"));

			return  userTeam;
		};
		
	}

}
