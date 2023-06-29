package com.ectd.global.eln.request;

import java.io.Serializable;

public class ProjectRequest extends Base implements Serializable {

	private static final long serialVersionUID = -1742284186538858233L;

	private Integer projectId;
    private String projectName;
    private Integer productId;
    private String productName;
    private String productCode;
    private String status;
    private String strength;
    private Integer dosageId;
    private String dosageName;
    private Integer formulationId;
    private String formulationName;
    private Integer teamId;
    private String teamName;
    private Integer marketId;
    private String markertName;
    
    private ProjectTeamRequest projectTeam;
    
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
		NEW("New"), INPROGRESS("Inprogress"), COMPLETED("Completed"), INACTIVE("Inactive"), CLOSED("Closed"), ARCHIVE("Archive");
		
		private String value;
		
		PROJECT_STATUS(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getDosageName() {
		return dosageName;
	}
	public void setDosageName(String dosageName) {
		this.dosageName = dosageName;
	}
	
	public String getFormulationName() {
		return formulationName;
	}
	public void setFormulationName(String formulationName) {
		this.formulationName = formulationName;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getMarkertName() {
		return markertName;
	}
	public void setMarkertName(String markertName) {
		this.markertName = markertName;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public ProjectTeamRequest getProjectTeam() {
		return projectTeam;
	}
	public void setProjectTeam(ProjectTeamRequest projectTeam) {
		this.projectTeam = projectTeam;
	}
	
}


