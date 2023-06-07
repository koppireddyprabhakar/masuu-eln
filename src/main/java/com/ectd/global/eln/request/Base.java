package com.ectd.global.eln.request;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class Base {

	private Date insertDate;
	private String insertUser;
	private Date updateDate;
	private String updateUser;
	private String status;

	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getInsertUser() {
		return insertUser;
	}
	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}
	
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getInsertUserId() {
	    if (StringUtils.isBlank(insertUser) || !StringUtils.isNumeric(insertUser)) {
	        return 0; // or handle the invalid input case in a different way
	    }
	    
	    return Integer.parseInt(insertUser);
	}


}
