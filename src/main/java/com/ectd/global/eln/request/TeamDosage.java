package com.ectd.global.eln.request;

import java.io.Serializable;

public class TeamDosage extends Base implements Serializable {
	
	private static final long serialVersionUID = -2754907570115275042L;
	
	private Integer teamId;
	private Integer dosageId;
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public Integer getDosageId() {
		return dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}
	
	

}
