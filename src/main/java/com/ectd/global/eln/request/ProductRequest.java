package com.ectd.global.eln.request;

import java.io.Serializable;

public class ProductRequest extends Base implements Serializable {

	private static final long serialVersionUID = 8669949832582533289L;

	private Integer productId ;
	private String productName;
	private String productCode;
	
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

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Override
	public String toString() { 
		StringBuilder sb = new StringBuilder(); 
		sb.append(productId);
		sb.append("|");
		sb.append(productName);
		return sb.toString(); 
	}
	 
}
