package com.ectd.global.eln.request;

import java.io.Serializable;

public class MarketRequest extends Base implements Serializable{
	
	private static final long serialVersionUID = 8541683606304554491L;

	private Integer marketId; 
    private String marketName;
    
    public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
}
