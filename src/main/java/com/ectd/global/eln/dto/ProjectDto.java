package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ProjectDto extends Base implements Serializable {

	private static final long serialVersionUID = 5197779601046619899L;

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
}
