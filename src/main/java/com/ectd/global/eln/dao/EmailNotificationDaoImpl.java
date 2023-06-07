package com.ectd.global.eln.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.request.EmailNotification;

@Repository
@PropertySource(value={"classpath:sql/email-notification-dao.properties"})
public class EmailNotificationDaoImpl implements EmailNotificationDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value(value = "${createEmailNotification}")
	private String createEmailNotificationQuery;
	
	@Value("${get.email.notifications.to.send}")
	private String GET_EMAIL_NOTIFICATIONS_TO_SEND_QUERY;
	
	@Value(value = "${update.notification.type}")
    private String UPDATE_NOTIFICATION_TYPE_QUERY;
	

	public Integer saveNotification(EmailNotification emailNotification) {

	    KeyHolder keyHolder = new GeneratedKeyHolder();

	    MapSqlParameterSource parameters = new MapSqlParameterSource();
	    String ccList = "";
	    if(emailNotification.getEmailCc() != null ) {
	    	 ccList = String.join(",", emailNotification.getEmailCc());
	    } 
	    
	    parameters.addValue("emailCc", ccList);
	    parameters.addValue("emailReceiver", emailNotification.getEmailReceiver());
	    parameters.addValue("emailBody", emailNotification.getEmailBody());
	    parameters.addValue("emailSubject", emailNotification.getEmailSubject());
	    parameters.addValue("emailNotificationType", emailNotification.getEmailNotificationType());

	    namedParameterJdbcTemplate.update(createEmailNotificationQuery, parameters, keyHolder);

	    return keyHolder.getKey().intValue();
	}

	@Override
	public List<EmailNotification> getEmailNotificationsToSend() {
		return jdbcTemplate.query(GET_EMAIL_NOTIFICATIONS_TO_SEND_QUERY, new BeanPropertyRowMapper<>(EmailNotification.class));
	}

	 @Override
	    public void updateNotificationType(long emnId, int notificationType) {
	        jdbcTemplate.update(UPDATE_NOTIFICATION_TYPE_QUERY, notificationType, emnId);
	    }

		@Override
		public void updateEmailNotificationStatus(List<Long> emnIds, int notificationType) {
			String query = "UPDATE EMAIL_NOTIFICATION SET NOTIFICATION_TYPE = ? WHERE EMN_ID = ?";
			List<Object[]> batchArgs = new ArrayList<>();
			for (Long emnId : emnIds) {
				Object[] args = new Object[] { notificationType, emnId };
				batchArgs.add(args);
			}
			jdbcTemplate.batchUpdate(query, batchArgs);
		}
}
