package com.ectd.global.eln.criteria;

public class MarketCriteria {

    private String marketName; 
    private String insertProcess; 

	public MarketCriteria() {
	}

	public void setMarketName(String marketName) {this.marketName = marketName;}
	public String getMarketName() {return this.marketName;}
	public void setInsertProcess(String insertProcess) {this.insertProcess = insertProcess;}
	public String getInsertProcess() {return this.insertProcess;}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(marketName); 
		sb.append("|"); 
		sb.append(insertProcess); 
        return sb.toString();
	}
}

