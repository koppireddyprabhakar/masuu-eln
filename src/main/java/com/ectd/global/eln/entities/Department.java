package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="DEPARTMENT", schema="dbo", catalog="ELN" )
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="DEPT_ID", nullable=false)
    private Integer deptId;
    
    @Column(name="DEPARTMENT_NAME", nullable=false, length=50)
    private String departmentName ;     
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate ;     
    
    @Column(name="INSERT_PROCESS", length=20)
    private String insertProcess ; 

    public Department() {
		super();
    }

	public void setDeptId(Integer deptId) {
        this.deptId = deptId ;
    }
	public Integer getDeptId() {
        return this.deptId;
    }

	public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
	public String getDepartmentName() {return this.departmentName;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	public Date getInsertDate() {return this.insertDate;}

	public void setInsertProcess(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertProcess() {return this.insertProcess;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(deptId); 
		sb.append(departmentName); 
		sb.append("|"); 
		sb.append(insertDate); 
		sb.append("|"); 
		sb.append(insertProcess); 
        return sb.toString();
    }
}