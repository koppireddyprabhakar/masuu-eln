package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.request.UserTeamRequest;
import com.ectd.global.eln.request.UsersDetailsRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/users-details-dao.properties"})
public class UsersDetailsDaoImpl implements UsersDetailsDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getUsersDetailsById}")
	private String getUsersDetailsByIdQuery;

	@Value(value="${getUsersDetailsList}")
	private String getUsersDetailsListQuery;

	@Value(value="${createUsersDetails}")
	private String createUsersDetailsQuery;

	@Value(value="${updateUsersDetails}")
	private String updateUsersDetailsQuery;

	@Value(value="${deleteUsersDetails}")
	private String deleteUsersDetailsQuery;

	@Value(value="${createUserTeam}")
	private String createUserTeamQuery;

	@Value(value="${updateUserTeam}")
	private String updateUserTeamQuery;

	@Override
	public UsersDetailsDto getUsersDetailsById(Integer usersDetailsId) {
		List<UsersDetailsDto> usersDetailsList = jdbcTemplate.query(getUsersDetailsByIdQuery + usersDetailsId,
				new UsersDetailsRowMapper());

		if(usersDetailsList.isEmpty()) {
			return null;
		}

		return usersDetailsList.get(0);
	}

	@Override
	public List<UsersDetailsDto> getUsersDetails() {
		return jdbcTemplate.query(getUsersDetailsListQuery, new UsersDetailsRowMapper());
	}

	@Override
	public Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		this.update(usersDetailsRequest);

		List<UserTeamRequest> updateUserTeams = usersDetailsRequest.getUserTeamRequests().stream().filter(ut -> !ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());
		List<UserTeamRequest> insertUserTeams = usersDetailsRequest.getUserTeamRequests().stream().filter(ut -> ObjectUtils.isEmpty(ut.getUserId())).collect(Collectors.toList());

		int[] updatedRows = null;
		if(!CollectionUtils.isEmpty(updateUserTeams)) {
			updatedRows = this.batchUpdate(updateUserTeams);
		}

		if(!CollectionUtils.isEmpty(insertUserTeams)) {
			this.batchInsert(insertUserTeams, usersDetailsRequest.getUserId());
		}

		return updatedRows != null ? updatedRows.length : 0;

	}

	private Integer update(UsersDetailsRequest usersDetailsRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("userId", usersDetailsRequest.getUserId());
		parameters.addValue("firstName", usersDetailsRequest.getFirstName());
		parameters.addValue("lastName", usersDetailsRequest.getLastName());
		parameters.addValue("dateOfBirth", usersDetailsRequest.getDateOfBirth());
		parameters.addValue("gender", usersDetailsRequest.getGender());
		parameters.addValue("deptId", usersDetailsRequest.getDeptId());
		parameters.addValue("roleId", usersDetailsRequest.getRoleId());
		parameters.addValue("contactNo", usersDetailsRequest.getContactNo());
		parameters.addValue("mailId", usersDetailsRequest.getMailId());
		parameters.addValue("status", usersDetailsRequest.getStatus());
		parameters.addValue("addressLine1", usersDetailsRequest.getAddressLine1());
		parameters.addValue("addressLine2", usersDetailsRequest.getAddressLine2());
		parameters.addValue("city", usersDetailsRequest.getCity());
		parameters.addValue("zipCode", usersDetailsRequest.getZipCode());
		parameters.addValue("updateUser", usersDetailsRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(updateUsersDetailsQuery, parameters);
	}

	private int[] batchUpdate(List<UserTeamRequest> userTeamRequests) {

		userTeamRequests.forEach(f -> {
			f.setUpdateUser("ELN");
			f.setUpdateDate(ElnUtils.getTimeStamp());
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(userTeamRequests.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( updateUserTeamQuery, batch);
	}

	@Override
	public Integer deleteUsersDetails(Integer usersDetailsId) {
		return jdbcTemplate.update(deleteUsersDetailsQuery, new Object[] {usersDetailsId});
	}

	@Override
	public Boolean createUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		Integer userId = this.create(usersDetailsRequest);
		int[] insertedRows = this.batchInsert(usersDetailsRequest.getUserTeamRequests(), userId);

		if(insertedRows.length > 0) {
			return true;
		}

		return false;
	}

	private Integer create(UsersDetailsRequest usersDetailsRequest) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("firstName", usersDetailsRequest.getFirstName());
		parameters.addValue("lastName", usersDetailsRequest.getLastName());
		parameters.addValue("dateOfBirth", usersDetailsRequest.getDateOfBirth());
		parameters.addValue("gender", usersDetailsRequest.getGender());
		parameters.addValue("deptId", usersDetailsRequest.getDeptId());
		parameters.addValue("roleId", usersDetailsRequest.getRoleId());
		parameters.addValue("contactNo", usersDetailsRequest.getContactNo());
		parameters.addValue("mailId", usersDetailsRequest.getMailId());
		parameters.addValue("status", usersDetailsRequest.getStatus());
		parameters.addValue("addressLine1", usersDetailsRequest.getAddressLine1());
		parameters.addValue("addressLine2", usersDetailsRequest.getAddressLine2());
		parameters.addValue("city", usersDetailsRequest.getCity());
		parameters.addValue("zipCode", usersDetailsRequest.getZipCode());
		parameters.addValue("insertUser", usersDetailsRequest.getInsertUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		parameters.addValue("updateUser", usersDetailsRequest.getUpdateUser());
		parameters.addValue("updateDate", ElnUtils.getTimeStamp());

		namedParameterJdbcTemplate.update(createUsersDetailsQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	private int[] batchInsert(List<UserTeamRequest> userTeamRequests, Integer userId) {

		userTeamRequests.forEach(f -> 
		{
			f.setUserId(userId);
			f.setInsertDate(ElnUtils.getTimeStamp());
			f.setInsertUser("ELN");
			f.setUpdateDate(ElnUtils.getTimeStamp());
			f.setUpdateUser("ELN");
		});

		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(userTeamRequests.toArray());
		return this.namedParameterJdbcTemplate.batchUpdate( createUserTeamQuery, batch);
	}

	class UsersDetailsRowMapper implements RowMapper<UsersDetailsDto> {
		public UsersDetailsDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			UsersDetailsDto usersDetailsDto = new UsersDetailsDto();
			usersDetailsDto.setUserId(resultSet.getInt("USER_ID"));
			usersDetailsDto.setFirstName(resultSet.getString("FIRST_NAME"));
			usersDetailsDto.setLastName(resultSet.getString("LAST_NAME"));
			usersDetailsDto.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
			usersDetailsDto.setGender(resultSet.getString("GENDER"));
			usersDetailsDto.setDeptId(resultSet.getInt("DEPT_ID"));
			usersDetailsDto.setRoleId(resultSet.getInt("ROLE_ID"));
			usersDetailsDto.setContactNo(resultSet.getInt("CONTACT_NO"));
			usersDetailsDto.setMailId(resultSet.getString("MAIL_ID"));
			usersDetailsDto.setStatus(resultSet.getString("STATUS"));
			usersDetailsDto.setAddressLine1(resultSet.getString("ADDRESS_LINE1"));
			usersDetailsDto.setAddressLine2(resultSet.getString("ADDRESS_LINE2"));
			usersDetailsDto.setCity(resultSet.getString("CITY"));
			usersDetailsDto.setZipCode(resultSet.getString("ZIP_CODE"));
			usersDetailsDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			usersDetailsDto.setInsertUser(resultSet.getString("INSERT_USER"));
			usersDetailsDto.setUpdateDate(resultSet.getDate("UPDATE_DATE"));
			usersDetailsDto.setUpdateUser(resultSet.getString("UPDATE_USER"));
			usersDetailsDto.setRoleName(resultSet.getString("ROLE_NAME"));
			usersDetailsDto.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
			usersDetailsDto.setTeamId(resultSet.getInt("TEAM_ID"));

			return usersDetailsDto;
		};
	}
}
