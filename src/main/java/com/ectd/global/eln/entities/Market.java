package com.ectd.global.eln.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="MARKET", schema="dbo", catalog="ELN" )
public class Market implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name="MARKET_ID", nullable=false)
    private Integer marketId ; 

    @Column(name="MARKET_NAME", nullable=false, length=50)
    private String marketName ;     
    
    @Temporal(TemporalType.DATE)
    @Column(name="INSERT_DATE")
    private Date insertDate ;     
    
    @Column(name="INSERT_USER", length=20)
    private String insertProcess ; 

    public Market() {
		super();
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId ;
    }
	
    public Integer getMarketId() {
        return this.marketId;
    }

    public void setMarketName(String marketName) {this.marketName = marketName;}
	
    public String getMarketName() {return this.marketName;}

	public void setInsertDate(Date insertDate) {this.insertDate = insertDate;}
	
	public Date getInsertDate() {return this.insertDate;}

	public void setInsertUser(String insertProcess) {this.insertProcess = insertProcess;}
	
	public String getInsertUser() {return this.insertProcess;}

	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(marketId); 
		sb.append(marketName); 
		sb.append("|"); 
		sb.append(insertDate); 
		sb.append("|"); 
		sb.append(insertProcess); 
        return sb.toString();
    }
}