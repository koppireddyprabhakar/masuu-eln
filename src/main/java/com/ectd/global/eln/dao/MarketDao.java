package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.MarketDto;
import com.ectd.global.eln.request.MarketRequest;

public interface MarketDao {
	MarketDto getMarketById(Integer productId);
	List<MarketDto> getMarkets();
	Integer createMarket(MarketRequest marketRequest);
	Integer updateMarket(MarketRequest marketRequest);
	Integer deleteMarket(Integer productId);
}
