package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="USER_ROLE", schema="dbo", catalog="ELN" )
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="ROLE_ID", nullable=false)
    private Integer roleId;

    @Column(name="ROLE_NAME", nullable=false, length=50)
    private String roleName;
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name="UPDATE_DATE")
    private Date updateDate;

    public UserRole() {
		super();
    }

	public void setRoleId(Integer roleId) {
        this.roleId = roleId ;
    }
	public Integer getRoleId() {
        return this.roleId;
    }

	public void setRoleName(String roleName) {this.roleName = roleName;}
	public String getRoleName() {return this.roleName;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	public Date getInsertDate() {return this.insertDate;}

	public void setUpdateDate(Date updateDate) {this.updateDate = updateDate;}
	public Date getUpdateDate() {return this.updateDate;}

 	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(roleId); 
		sb.append(roleName); 
		sb.append("|"); 
		sb.append(insertDate); 
		sb.append("|"); 
		sb.append(updateDate); 
        return sb.toString();
    }
}