package com.ectd.global.eln.audit;

import java.sql.Timestamp;

public class AuditLog {
	
	 private Long id;
	 private Integer userId;
	 private String userName;
	 private String action;
	 private Timestamp createdDate;
	 private String eventType;
	 private String moduleSection;
	 private String ipAddress;
    
    public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getModuleSection() {
		return moduleSection;
	}
	public void setModuleSection(String moduleSection) {
		this.moduleSection = moduleSection;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
		
}
