package com.ectd.global.eln.audit;

import java.sql.Timestamp;


public class AuditLogDto {
	
	
	private Long id;
    private String userName; // User Identifier
    private String action; // Action Taken
    private Timestamp createdDate; // Timestamp of the Action
    private String eventType;              // Event
    private String moduleSection;      // Module / Section
    private String ipAddress;          // IP Address
    
    private String fromDate;
    private String toDate;
    
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
    
}
