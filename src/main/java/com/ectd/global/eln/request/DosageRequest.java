package com.ectd.global.eln.request;

import java.io.Serializable;
import java.util.List;

import com.ectd.global.eln.dto.FormulationDto;

public class DosageRequest extends Base implements Serializable {
	
	private static final long serialVersionUID = 903063372204221290L;
	
	private Integer dosageId; 
	private String dosageName;
	
	private List<FormulationDto> formulations;
	
	public Integer getDosageId() {
		return dosageId;
	}
	public void setDosageId(Integer dosageId) {
		this.dosageId = dosageId;
	}
	
	public String getDosageName() {
		return dosageName;
	}
	public void setDosageName(String dosageName) {
		this.dosageName = dosageName;
	}
	
	public List<FormulationDto> getFormulations() {
		return formulations;
	}
	public void setFormulations(List<FormulationDto> formulations) {
		this.formulations = formulations;
	}

}
