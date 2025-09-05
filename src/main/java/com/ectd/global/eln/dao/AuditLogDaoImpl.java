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

import com.ectd.global.eln.audit.AuditLog;
import com.ectd.global.eln.audit.AuditLogDto;

@Repository
@PropertySource(value = {"classpath:sql/auditlog-dao.properties"})
public class AuditLogDaoImpl implements AuditLogDao{
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	 @Qualifier("namedParameterJdbcTemplate")
     private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 
	 @Value("${get.audit.logs}")
	 private String GET_AUDITLOG_LIST;
	 
	 @Value("${get.audit.logs.by.userid}")
	 private String GET_AUDITLOG_BY_USERID;
	 
	 @Value("${create.audit.logs}")
	 private String CREATE_AUDITLOG_QUERY;
	 
	 

	@Override
	public List<AuditLogDto> getAllAuditLogs() {
		 return jdbcTemplate.query(GET_AUDITLOG_LIST, new AuditLogRowMapper());
	}
	
	@Override
	public List<AuditLogDto> getAuditLogsByUserId(int userId) {
	    return jdbcTemplate.query( GET_AUDITLOG_BY_USERID, new Object[]{userId},new AuditLogRowMapper());
	}


	public Integer saveAuditLog(AuditLog auditLog) {
	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    parameters.addValue("userId", auditLog.getUserId());
	    parameters.addValue("userName", auditLog.getUserName());
	    parameters.addValue("action", auditLog.getAction());
	    parameters.addValue("createdDate", auditLog.getCreatedDate());
	    parameters.addValue("eventType", auditLog.getEventType());
	    parameters.addValue("moduleSection", auditLog.getModuleSection());
	    parameters.addValue("ipAddress", auditLog.getIpAddress());

	    return namedParameterJdbcTemplate.update(CREATE_AUDITLOG_QUERY, parameters);
	}

		
	
	
	public class AuditLogRowMapper implements RowMapper<AuditLogDto> {
		  @Override
		    public AuditLogDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		        AuditLogDto dto = new AuditLogDto();
		        dto.setId(rs.getLong("ID"));
		        dto.setUserName(rs.getString("USER_NAME"));
		        dto.setAction(rs.getString("ACTION"));
		        dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
		        dto.setEventType(rs.getString("EVENT_TYPE"));
		        dto.setModuleSection(rs.getString("MODULE_SECTION"));
		        dto.setIpAddress(rs.getString("IP_ADDRESS"));
		        return dto;
		    }
	}

}
