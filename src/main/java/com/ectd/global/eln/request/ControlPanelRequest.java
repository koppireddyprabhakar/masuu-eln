package com.ectd.global.eln.request;

import java.io.Serializable;

public class ControlPanelRequest extends Base implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer usersLimit;  
    private Integer numberOfUsers; 
    private String licenceStartDate; 
    private String licenceExpiryDate;  

   
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

   
    public String getLicenceStartDate() {
        return licenceStartDate;
    }

    public void setLicenceStartDate(String licenceStartDate) {
        this.licenceStartDate = licenceStartDate;
    }

    
    public String getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(String licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }
    
}
