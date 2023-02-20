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

import com.ectd.global.eln.dto.MarketDto;
import com.ectd.global.eln.request.MarketRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/market-dao.properties"})
public class MarketDaoImpl implements MarketDao{

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value(value="${getMarketById}")
	private String getMarketByIdQuery;

	@Value(value="${getMarketList}")
	private String getMarketListQuery;

	@Value(value="${createMarket}")
	private String createMarketQuery;

	@Value(value="${updateMarket}")
	private String updateMarketQuery;

	@Value(value="${deleteMarket}")
	private String deleteMarketQuery;

	@Override
	public MarketDto getMarketById(Integer marketId) {
		List<MarketDto> markets = jdbcTemplate.query(getMarketByIdQuery + marketId,
				new MarketRowMapper());

		if(markets.isEmpty()) {
			return null;
		}

		return markets.get(0);
	}

	@Override
	public List<MarketDto> getMarkets() {
		return jdbcTemplate.query(getMarketListQuery, new MarketRowMapper());
	}

	@Override
	public Integer createMarket(MarketRequest marketRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("marketName", marketRequest.getMarketName());
		parameters.addValue("status", ElnUtils.STATUS.ACTIVE.getValue());
		parameters.addValue("insertUser", marketRequest.getInsertUser());
		parameters.addValue("insertDate", ElnUtils.getTimeStamp());

		return namedParameterJdbcTemplate.update(createMarketQuery, parameters);
	}

	@Override
	public Integer updateMarket(MarketRequest marketRequest) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("marketId", marketRequest.getMarketId());
		parameters.addValue("marketName", marketRequest.getMarketName());
		parameters.addValue("status", marketRequest.getStatus());

		return namedParameterJdbcTemplate.update(updateMarketQuery, parameters);
	}

	@Override
	public Integer deleteMarket(Integer marketId) {
		return jdbcTemplate.update(deleteMarketQuery, new Object[] {marketId});
	}

	class MarketRowMapper implements RowMapper<MarketDto> {
		public MarketDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			MarketDto marketDto = new MarketDto();
			marketDto.setMarketId(resultSet.getInt("MARKET_ID"));
			marketDto.setMarketName(resultSet.getString("MARKET_NAME"));
			marketDto.setStatus(resultSet.getString("STATUS"));
			marketDto.setInsertDate(resultSet.getDate("INSERT_DATE"));
			marketDto.setInsertUser(resultSet.getString("INSERT_USER"));
			
			return marketDto;
		};
	}	
}