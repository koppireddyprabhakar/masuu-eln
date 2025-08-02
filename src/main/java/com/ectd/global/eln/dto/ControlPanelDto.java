package com.ectd.global.eln.dto;

import java.io.Serializable;
import java.util.Date;

public class ControlPanelDto   implements Serializable {

    private static final long serialVersionUID = -8931142530477857614L;; 
	

    private Integer usersLimit;  
    private Integer numberOfUsers; 
    private Date licenceStartDate; 
    private Date licenceExpiryDate;  

    public Integer getUsersLimit() {
        return usersLimit;
    }

    public void setUsersLimit(Integer usersLimit) {
        this.usersLimit = usersLimit;
    }

   
    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

   
    public java.util.Date getLicenceStartDate() {
        return licenceStartDate;
    }

    public void setLicenceStartDate(Date date) {
        this.licenceStartDate = date;
    }

    
    public Date getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(Date date) {
        this.licenceExpiryDate = date;
    }
	

}

