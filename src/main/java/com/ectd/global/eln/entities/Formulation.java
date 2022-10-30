package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="FORMULATION", schema="dbo", catalog="ELN" )
public class Formulation implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="FORMULATION_ID", nullable=false)
    private Integer formulationId;
    
    @Column(name="FORMULATION_NAME", nullable=false, length=50)
    private String formulationName ;     
    
    @Column(name="DOSAGE_ID")
    private Integer dosageId ;     
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate ;     
    
    @Column(name="INSERT_USER", length=20)
    private String insertProcess ;     
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate ;     
    
    @Column(name="UPDATE_USER", length=20)
    private String updateProcess ; 

    public Formulation() {
		super();
    }

    public void setFormulationId(Integer formulationId) {
        this.formulationId = formulationId ;
    }
	public Integer getFormulationId() {
        return this.formulationId;
    }

    public void setFormulationName(String formulationName) {this.formulationName = formulationName;}
	public String getFormulationName() {return this.formulationName;}

	public void setDosageId(Integer dosageId) {this.dosageId = dosageId;}
	public Integer getDosageId() {return this.dosageId;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	public Date getInsertDate() {return this.insertDate;}

	public void setInsertUser(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertUser() {return this.insertProcess;}

	public void setUpdateDate(Date updateDate) {this.updateDate = updateDate;}
	public Date getUpdateDate() {return this.updateDate;}

	public void setUpdateUser(String updateProcess) {this.updateProcess = updateProcess;}
	public String getUpdateUser() {return this.updateProcess;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(formulationId); 
		sb.append(formulationName); 
		sb.append("|"); 
		sb.append(dosageId); 
		sb.append("|"); 
		sb.append(insertDate); 
		sb.append("|"); 
		sb.append(insertProcess); 
		sb.append("|"); 
		sb.append(updateDate); 
		sb.append("|"); 
		sb.append(updateProcess); 
        return sb.toString();
    }
}