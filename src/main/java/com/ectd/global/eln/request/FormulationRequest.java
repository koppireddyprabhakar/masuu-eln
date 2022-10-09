package com.ectd.global.eln.request;

import java.io.Serializable;

public class FormulationRequest extends Base implements Serializable {
	
	private static final long serialVersionUID = 402152980514542671L;
	
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
