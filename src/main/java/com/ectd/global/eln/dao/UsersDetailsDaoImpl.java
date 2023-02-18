package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.ectd.global.eln.dto.UserTeamDto;
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

	@Value(value="${get.user.details.list}")
	private String GET_USER_DETAILS_LIST_QUERY;

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
				new UsersDetailsExtractor());

		if(usersDetailsList.isEmpty()) {
			return null;
		}

		return usersDetailsList.get(0);
	}

	@Override
	public List<UsersDetailsDto> getUsersDetails(Integer roleId, String departmentName) {
		
		StringBuilder sb = new StringBuilder(GET_USER_DETAILS_LIST_QUERY);
		
		if(roleId != null) {
			sb.append(" AND U.ROLE_ID = ").append(roleId);
		}
		
		if(departmentName != null) {
			sb.append(" AND D.DEPARTMENT_NAME = '").append(departmentName+"'");
		}
		
		sb.append(" ORDER BY U.INSERT_DATE DESC");
		
		List<UsersDetailsDto> sortedList = jdbcTemplate.query(sb.toString(), new UsersDetailsExtractor());
		 sortedList = sortedList.stream()
				.sorted(Comparator.comparingInt(UsersDetailsDto::getUserId)
				.reversed())
				.collect(Collectors.toList());
		return sortedList;
	}

	@Override
	public Integer updateUsersDetails(UsersDetailsRequest usersDetailsRequest) {
		
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

	public int[] batchUpdate(List<UserTeamRequest> userTeamRequests) {

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
		usersDetailsRequest.setPassword("eln@123456");
		Integer userId = this.create(usersDetailsRequest);
		int[] insertedRows = this.batchInsert(usersDetailsRequest.getUserTeams(), userId);

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
		parameters.addValue("password", usersDetailsRequest.getPassword());
		parameters.addValue("firstLogin", usersDetailsRequest.getFirstLogin());

		namedParameterJdbcTemplate.update(createUsersDetailsQuery, parameters, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public int[] batchInsert(List<UserTeamRequest> userTeamRequests, Integer userId) {

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
			return getUserDetails(resultSet);
		};
	}
	
	class UsersDetailsExtractor implements ResultSetExtractor<List<UsersDetailsDto>> {

		@Override
		public List<UsersDetailsDto> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
			List<UsersDetailsDto> analysisDtoList = new ArrayList<UsersDetailsDto>();

			while(resultSet.next()) {
				UsersDetailsDto usersDetailsDto = getUserDetails(resultSet);

				UserTeamDto userTeam = getUserTeam(resultSet);

				if(CollectionUtils.contains(analysisDtoList.iterator(), usersDetailsDto)) {
					int index = analysisDtoList.indexOf(usersDetailsDto);
					analysisDtoList.get(index).getUserTeams().add(userTeam);	
				} else {

					Set<UserTeamDto> userTeams = new LinkedHashSet<>();

					userTeams.add(userTeam);
					usersDetailsDto.setUserTeams(userTeams);
					analysisDtoList.add(usersDetailsDto);
				}

			}
			return analysisDtoList;
		};
	}
	
	public UsersDetailsDto getUserDetails(ResultSet resultSet) throws SQLException {
		UsersDetailsDto usersDetailsDto = new UsersDetailsDto();
		usersDetailsDto.setUserId(resultSet.getInt("USER_ID"));
		usersDetailsDto.setFirstName(resultSet.getString("FIRST_NAME"));
		usersDetailsDto.setLastName(resultSet.getString("LAST_NAME"));
		usersDetailsDto.setDateOfBirth(resultSet.getDate("DATE_OF_BIRTH"));
		usersDetailsDto.setGender(resultSet.getString("GENDER"));
		usersDetailsDto.setDeptId(resultSet.getInt("DEPT_ID"));
		usersDetailsDto.setRoleId(resultSet.getInt("ROLE_ID"));
		usersDetailsDto.setContactNo(resultSet.getString("CONTACT_NO"));
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
	}
	
	public UserTeamDto getUserTeam(ResultSet resultSet) throws SQLException {
		UserTeamDto usersDetailsDto = new UserTeamDto();
		usersDetailsDto.setUserId(resultSet.getInt("USER_ID"));
		usersDetailsDto.setTeamId(resultSet.getInt("TEAM_ID"));

		return usersDetailsDto;
	}
	
}
