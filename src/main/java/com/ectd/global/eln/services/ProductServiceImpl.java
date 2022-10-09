package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ProductDao;
import com.ectd.global.eln.dto.ProductDto;
import com.ectd.global.eln.request.ProductRequest;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ProductDto getProductById(Integer productId) {
		return productRepository.getProductById(productId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ProductDto> getProducts() {
		return productRepository.getProducts();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createProduct(ProductRequest productRequest) {
		return productRepository.createProduct(productRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateProduct(ProductRequest productRequest) {
		return productRepository.updateProduct(productRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteProduct(Integer productId) {
		return productRepository.deleteProduct(productId);
	}

}
