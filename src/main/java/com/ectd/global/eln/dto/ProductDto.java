package com.ectd.global.eln.dto;

import java.io.Serializable;

import com.ectd.global.eln.request.Base;

public class ProductDto extends Base implements Serializable {
	
	private static final long serialVersionUID = -7931742530427857314L;
	
	private Integer productId;
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

}
