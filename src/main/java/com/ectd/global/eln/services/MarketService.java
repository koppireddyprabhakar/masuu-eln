package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.MarketDto;
import com.ectd.global.eln.request.MarketRequest;

public interface MarketService {

	MarketDto getMarketById(Integer marketId);
	List<MarketDto> getMarkets();
	Integer createMarket(MarketRequest marketRequest);
	Integer updateMarket(MarketRequest marketRequest);
	Integer deleteMarket(Integer marketId);

}
