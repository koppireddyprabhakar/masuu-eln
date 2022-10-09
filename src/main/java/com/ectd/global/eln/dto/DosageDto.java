package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.List;

import com.ectd.global.eln.request.Base;

public class DosageDto extends Base implements Serializable {

	private static final long serialVersionUID = -6697657410962827386L;

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
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(dosageId); 
		sb.append(dosageName); 
		return sb.toString();
	}
	
	
	@Override
	public boolean equals(Object o) {
	    if (o == this)
	        return true;
	    if (!(o instanceof DosageDto))
	        return false;
	    DosageDto other = (DosageDto)o;
	    boolean dosageNameEquals = (this.dosageName == null && other.dosageName == null)
	      || (this.dosageName != null && this.dosageName.equals(other.dosageName));
	    return this.dosageId == other.dosageId && dosageNameEquals;
	}
	
	@Override
	public final int hashCode() {
	    int result = 17;
	    if (dosageId != null) {
	        result = 31 * result + dosageId.hashCode();
	    }
	    if (dosageName != null) {
	        result = 31 * result + dosageName.hashCode();
	    }
	    return result;
	}
}
