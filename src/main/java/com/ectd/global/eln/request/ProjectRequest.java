package com.ectd.global.eln.request;

import java.io.Serializable;

public class ProjectRequest extends Base implements Serializable {

	private static final long serialVersionUID = -1742284186538858233L;

	private Integer projectId;
    private String projectName;
    private Integer productId;
    private String status;
    private String strength;
    private Integer dosageId;
    private Integer formulationId;
    private Integer teamId;
    private Integer marketId;
	
    public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	
	public Integer getDosageId() {
		return dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}
	
	public Integer getFormulationId() {
		return formulationId;
	}
	public void setFormulationId(Integer formulationId) {
		this.formulationId = formulationId;
	}
	
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public static enum PROJECT_STATUS {
		NEW, INPROGRESS, COMPLETED, CLOSED, ARCHIVE
	}
	
}


