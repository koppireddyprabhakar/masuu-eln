package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.MarketDao;
import com.ectd.global.eln.dto.MarketDto;
import com.ectd.global.eln.request.MarketRequest;

@Service
public class MarketServiceImpl implements MarketService {

	@Autowired
	MarketDao marketDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public MarketDto getMarketById(Integer marketId) {
		return marketDao.getMarketById(marketId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MarketDto> getMarkets() {
		return marketDao.getMarkets();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createMarket(MarketRequest marketRequest) {
		return marketDao.createMarket(marketRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateMarket(MarketRequest marketRequest) {
		return marketDao.updateMarket(marketRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteMarket(Integer marketId) {
		return marketDao.deleteMarket(marketId);
	}

}
