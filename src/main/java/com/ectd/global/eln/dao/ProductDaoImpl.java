package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.ProductDto;
import com.ectd.global.eln.request.ProductRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/product-dao.properties"})
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getProductById}")
	private String getProductByIdQuery;

	@Value(value="${getProductList}")
	private String getProductListQuery;

	@Value(value="${createProduct}")
	private String createProductQuery;

	@Value(value="${updateProduct}")
	private String updateProductQuery;

	@Value(value="${deleteProduct}")
	private String deleteProductQuery;

	@Override
	public ProductDto getProductById(Integer productId) {
		List<ProductDto> products = jdbcTemplate.query(getProductByIdQuery + productId,
				new ProductRowMapper());

		if(products.isEmpty()) {
			return null;
		}

		return products.get(0);
	}

	@Override
	public List<ProductDto> getProducts() {
		return jdbcTemplate.query(getProductListQuery, new ProductRowMapper());
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		//		parameters.addValue("productId", productRequest.getProductId());
		parameters.addValue("productName", productRequest.getProductName());
		parameters.addValue("productCode", productRequest.getProductCode());
		parameters.addValue("status", productRequest.getStatus());
		parameters.addValue("insertUser", productRequest.getInsertUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(createProductQuery, parameters);
	}

	@Override
	public Integer updateProduct(ProductRequest productRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("productId", productRequest.getProductId());
		parameters.addValue("productName", productRequest.getProductName());
		parameters.addValue("productCode", productRequest.getProductCode());
		parameters.addValue("status", productRequest.getStatus());

		return namedParameterJdbcTemplate.update(updateProductQuery, parameters);
	}

	@Override
	public Integer deleteProduct(Integer productId) {
		return jdbcTemplate.update(deleteProductQuery, new Object[] {productId});
	}

	class ProductRowMapper implements RowMapper<ProductDto> {
		public ProductDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			
			ProductDto productDto = new ProductDto();
			productDto.setProductId(resultSet.getInt("PRODUCT_ID"));
			productDto.setProductName(resultSet.getString("PRODUCT_NAME"));
			productDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			productDto.setInsertUser(resultSet.getString("INSERT_USER"));
			
			return  productDto;
		};
	}

}
