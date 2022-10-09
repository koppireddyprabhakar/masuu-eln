package com.ectd.global.eln.request;

import java.util.Date;

public class Base {

	private Date insertDate;
	private String insertProcess;
	private Date updateDate;
	private String updateProcess;

	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getInsertProcess() {
		return insertProcess;
	}
	public void setInsertProcess(String insertProcess) {
		this.insertProcess = insertProcess;
	}

	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateProcess() {
		return updateProcess;
	}
	public void setUpdateProcess(String updateProcess) {
		this.updateProcess = updateProcess;
	}

}
