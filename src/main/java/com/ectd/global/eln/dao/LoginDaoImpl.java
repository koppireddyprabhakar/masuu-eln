package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.LoginDto;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = { "classpath:sql/login-dao.properties" })
public class LoginDaoImpl implements LoginDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value = "${SELECT_USER_DETAILS}")
	private String selectUserDetailsQuery;

	@Value(value = "${UPDATE_PASSWORD}")
	private String updatePasswordQuery;

	@SuppressWarnings("deprecation")
	@Override
	public LoginDto getUserDetails(String mailId) {
		try {
			return jdbcTemplate.queryForObject(selectUserDetailsQuery, new Object[] { mailId }, new LoginDTOMapper());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	@Override
	public boolean updatePassword(String mailId, String password) {
		String updatePasswordQuery = "UPDATE USERS_DETAILS SET PASSWORD = ?, FIRSTLOGIN = 0,  PASSWORD_UPDATED_DATE = ? WHERE MAIL_ID = ?";
		 int rowsAffected = jdbcTemplate.update(updatePasswordQuery, password, ElnUtils.getTimeStamp(), mailId);
		return rowsAffected > 0;
	}

	@Override
	public void saveOtp(String mailId, String otp, Timestamp timestamp) {
		jdbcTemplate.update("UPDATE USERS_DETAILS SET OTP = ?, TIMESTAMP = ? WHERE MAIL_ID = ?", otp, timestamp,
				mailId);
	}

	@Override
	public Timestamp getTimestamp(String mailId, String otp) {
		return jdbcTemplate.queryForObject("SELECT TIMESTAMP FROM USERS_DETAILS WHERE MAIL_ID = ? AND OTP = ?",
				Timestamp.class, mailId, otp);
	}

	@Override
	public int countEmail(String mailId) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERS_DETAILS WHERE MAIL_ID = ?", Integer.class,
				mailId);
	}
	
	 @Override
	    public void incrementFailedAttempts(String mailId) {
	        String sql = "UPDATE USERS_DETAILS SET failed_attempts = failed_attempts + 1 WHERE MAIL_ID = ?";
	        jdbcTemplate.update(sql, mailId);
	    }

	
	

	        @Override
	        public void lockAccount(String mailId) {
		    String sql = "UPDATE USERS_DETAILS SET account_locked = 1 WHERE mail_id = ?";
		     jdbcTemplate.update(sql, mailId);  	 
		}



	    
	    @Override
	    public int getFailedAttempts(String mailId) {
	        String sql = "SELECT failed_attempts FROM USERS_DETAILS WHERE MAIL_ID = ?";
	        Integer attempts = jdbcTemplate.queryForObject(sql, new Object[]{mailId}, Integer.class);
	        return attempts != null ? attempts : 0;
	    }
	    
	    public void resetFailedAttempts(String mailId) {
	        String resetFailedAttemptsQuery = "UPDATE USERS_DETAILS SET FAILED_ATTEMPTS = 0 WHERE MAIL_ID = ?";
	        jdbcTemplate.update(resetFailedAttemptsQuery, mailId);
	    }



	private final class LoginDTOMapper implements RowMapper<LoginDto> {
		@Override
		public LoginDto mapRow(ResultSet rs, int rowNum) throws SQLException {
			LoginDto login = new LoginDto();
			login.setMailId(rs.getString("MAIL_ID"));
			login.setPassword(rs.getString("PASSWORD"));
			login.setUserId(rs.getInt("USER_ID"));
			login.setFirstName(rs.getString("FIRST_NAME"));
			login.setLastName(rs.getString("LAST_NAME"));
			login.setDateOfBirth(rs.getDate("DATE_OF_BIRTH"));
			login.setGender(rs.getString("GENDER"));
			login.setDeptId(rs.getInt("DEPT_ID"));
			login.setRoleId(rs.getInt("ROLE_ID"));
			login.setContactNo(rs.getInt("CONTACT_NO"));
			login.setStatus(rs.getString("STATUS"));
			login.setAddressLine1(rs.getString("ADDRESS_LINE1"));
			login.setAddressLine2(rs.getString("ADDRESS_LINE2"));
			login.setCity(rs.getString("CITY"));
			login.setZipCode(rs.getString("ZIP_CODE"));
			login.setFirstLogin(rs.getString("FIRSTLOGIN"));
			login.setOtp(rs.getInt("OTP"));
			login.setTimeStamp(rs.getDate("TIMESTAMP"));
			login.setTeamId(rs.getInt("TEAM_ID"));
			login.setAccountLocked(rs.getInt("ACCOUNT_LOCKED") == 1); // Correctly checks for account lock status
			login.setPasswordUpdateDate(rs.getDate("PASSWORD_UPDATED_DATE"));
		    login.setSuperAdmin(rs.getInt("ROLE_ID") == 5);
			return login;
		}
	}
}