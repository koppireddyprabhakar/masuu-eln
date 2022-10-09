package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="PRODUCT", schema="dbo", catalog="ELN" )
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    public Product() {
		super();
    }
    
    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private Integer    productId ;

    @Column(name="PRODUCT_NAME", nullable=false, length=50)
    private String     productName ;

    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date       insertDate ;

    @Column(name="INSERT_PROCESS", length=20)
    private String     insertProcess ;
    
    //--- GETTERS & SETTERS FOR FIELDS
    public void setProductId( Integer productId ) {
        this.productId = productId ;
    }
    public Integer getProductId() {
        return this.productId;
    }

    public void setProductName( String productName ) {
        this.productName = productName ;
    }
    public String getProductName() {
        return this.productName;
    }

    public void setInsertDate( Date insertDate ) {
        this.insertDate = insertDate ;
    }
    public Date getInsertDate() {
        return this.insertDate;
    }

    public void setInsertProcess( String insertProcess ) {
        this.insertProcess = insertProcess ;
    }
    public String getInsertProcess() {
        return this.insertProcess;
    }

	@Override
    public String toString() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(productId);
        sb.append("|");
        sb.append(productName);
        sb.append("|");
        sb.append(insertDate);
        sb.append("|");
        sb.append(insertProcess);
        return sb.toString(); 
    } 

}

