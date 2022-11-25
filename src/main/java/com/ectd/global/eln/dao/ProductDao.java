package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ProductDto;
import com.ectd.global.eln.request.ProductRequest;

public interface ProductDao {

	ProductDto getProductById(Integer productId);
	
	List<ProductDto> getProducts();
	
	Integer createProduct(ProductRequest productRequest);
	
	Integer updateProduct(ProductRequest productRequest);
	
	Integer deleteProduct(ProductRequest productRequest);
	
}
