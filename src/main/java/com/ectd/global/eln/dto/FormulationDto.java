package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class FormulationDto extends Base implements Serializable{

	private static final long serialVersionUID = -715349331431222252L;

	private Integer formulationId;
    private String formulationName;     
    private Integer dosageId;
    
	public Integer getFormulationId() {
		return formulationId;
	}
	public void setFormulationId(Integer formulationId) {
		this.formulationId = formulationId;
	}
	
	public String getFormulationName() {
		return formulationName;
	}
	public void setFormulationName(String formulationName) {
		this.formulationName = formulationName;
	}
	
	public Integer getDosageId() {
		return dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}
}
