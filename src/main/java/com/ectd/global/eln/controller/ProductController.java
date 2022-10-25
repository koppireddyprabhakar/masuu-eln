package com.ectd.global.eln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.ProductDto;
import com.ectd.global.eln.request.ProductRequest;
import com.ectd.global.eln.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Autowired
	ProductService productService;
	
	@GetMapping("/get-product-by-id")
	public ResponseEntity<ProductDto> getProductById(@RequestParam Integer productId) throws Exception {
		return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
	}
	
	@GetMapping("/get-products")
	public ResponseEntity<List<ProductDto>> getProducts() throws Exception {
		return  new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
	}
	
	@PostMapping("/create-product")
	public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
		return getResponseEntity(productService.createProduct(productRequest), "Product Create");
	}
	
	@PutMapping("/update-product")
	public ResponseEntity<String> updateProduct(@RequestBody ProductRequest productRequest) {
		return getResponseEntity(productService.updateProduct(productRequest), "Product Update");
	}
	
	@DeleteMapping("/delete-product")
	public ResponseEntity<String> deleteProduct(@RequestParam Integer productId) throws Exception {
		return getResponseEntity(productService.deleteProduct(productId), "Product Delete");
	}
	
}
