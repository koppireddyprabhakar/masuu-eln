package com.ectd.global.eln.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.ectd.global.eln.dto.ProductDto;
import com.ectd.global.eln.request.ProductRequest;
import com.ectd.global.eln.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    /**
     * Method under test: {@link ProductController#getProductById(Integer)}
     */
    @Test
    void testGetProductById() throws Exception {
        ProductDto productDto = new ProductDto();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productDto.setInsertUser("Insert User");
        productDto.setProductCode("Product Code");
        productDto.setProductId(123);
        productDto.setProductName("Product Name");
        productDto.setStatus("Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        productDto.setUpdateUser("2020-03-01");
        when(this.productService.getProductById((Integer) any())).thenReturn(productDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/get-product-by-id");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("productId", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
                                        + "\",\"productId\":123,\"productName\":\"Product Name\",\"productCode\":\"Product Code\"}"));
    }

    /**
     * Method under test: {@link ProductController#getProductById(Integer)}
     */
    @Test
    void testGetProductById2() throws Exception {
        ProductDto productDto = new ProductDto();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productDto.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productDto.setInsertUser("Insert User");
        productDto.setProductCode("Product Code");
        productDto.setProductId(123);
        productDto.setProductName("Product Name");
        productDto.setStatus("Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productDto.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        productDto.setUpdateUser("2020-03-01");
        when(this.productService.getProductById((Integer) any())).thenReturn(productDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/get-product-by-id");
        getResult.contentType("application/json");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("productId", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"insertDate\":0,\"insertUser\":\"Insert User\",\"updateDate\":0,\"updateUser\":\"2020-03-01\",\"status\":\"Status"
                                        + "\",\"productId\":123,\"productName\":\"Product Name\",\"productCode\":\"Product Code\"}"));
    }

    /**
     * Method under test: {@link ProductController#getProducts()}
     */
    @Test
    void testGetProducts() throws Exception {
        when(this.productService.getProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/get-products");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProductController#getProducts()}
     */
    @Test
    void testGetProducts2() throws Exception {
        when(this.productService.getProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/product/get-products");
        getResult.contentType("application/json");
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ProductController#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct() throws Exception {
        when(this.productService.createProduct((ProductRequest) any())).thenReturn(1);

        ProductRequest productRequest = new ProductRequest();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productRequest.setInsertUser("Insert User");
        productRequest.setProductCode("Product Code");
        productRequest.setProductId(123);
        productRequest.setProductName("Product Name");
        productRequest.setStatus("Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        productRequest.setUpdateUser("2020-03-01");
        String content = (new ObjectMapper()).writeValueAsString(productRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/product/create-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Product Create Successfully\"}"));
    }

    /**
     * Method under test: {@link ProductController#updateProduct(ProductRequest)}
     */
    @Test
    void testUpdateProduct() throws Exception {
        when(this.productService.updateProduct((ProductRequest) any())).thenReturn(1);

        ProductRequest productRequest = new ProductRequest();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        productRequest.setInsertDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        productRequest.setInsertUser("Insert User");
        productRequest.setProductCode("Product Code");
        productRequest.setProductId(123);
        productRequest.setProductName("Product Name");
        productRequest.setStatus("Status");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        productRequest.setUpdateDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        productRequest.setUpdateUser("2020-03-01");
        String content = (new ObjectMapper()).writeValueAsString(productRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/product/update-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Product Update Successfully\"}"));
    }

    /**
     * Method under test: {@link ProductController#deleteProduct(Integer)}
     */
    @Test
    void testDeleteProduct() throws Exception {
        when(this.productService.deleteProduct((ProductRequest) any())).thenReturn(1);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/product/delete-product");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("productId", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Product Delete Successfully\"}"));
    }

    /**
     * Method under test: {@link ProductController#deleteProduct(Integer)}
     */
    @Test
    void testDeleteProduct2() throws Exception {
        when(this.productService.deleteProduct((ProductRequest) any())).thenReturn(1);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/product/delete-product");
        deleteResult.contentType("application/json");
        MockHttpServletRequestBuilder requestBuilder = deleteResult.param("productId", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(this.productController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("{\"data\":\"Product Delete Successfully\"}"));
    }
}

