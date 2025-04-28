package com.ectd.global.eln.audit;

import java.sql.Timestamp;


public class AuditLogDto {
	
	
	private Long id;
    private String userName; // User Identifier
    private String action; // Action Taken
    private Timestamp createdDate; // Timestamp of the Action
    
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
    
}
