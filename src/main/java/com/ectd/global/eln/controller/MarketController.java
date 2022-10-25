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

import com.ectd.global.eln.dto.MarketDto;
import com.ectd.global.eln.request.MarketRequest;
import com.ectd.global.eln.services.MarketService;

@RestController
@RequestMapping(value = "/market", produces = "application/json")
public class MarketController extends BaseController {
	@Autowired
	MarketService marketService;
	
	@GetMapping("/get-market-by-id")
	public ResponseEntity<MarketDto> getMarketById(@RequestParam Integer marketId) throws Exception {
		return new ResponseEntity<>(marketService.getMarketById(marketId), HttpStatus.OK);
	}
	
	@GetMapping("/get-markets")
	public ResponseEntity<List<MarketDto>> getMarkets() throws Exception {
		return  new ResponseEntity<>(marketService.getMarkets(), HttpStatus.OK);
	}
	
	@PostMapping("/create-market")
	public ResponseEntity<String> createMarket(@RequestBody MarketRequest marketRequest) {
		return getResponseEntity(marketService.createMarket(marketRequest), "Market Create");
	}
	
	@PutMapping("/update-market")
	public ResponseEntity<String> updateMarket(@RequestBody MarketRequest marketRequest) {
		return getResponseEntity(marketService.updateMarket(marketRequest), "Market Update");
	}
	
	@DeleteMapping("/delete-market")
	public ResponseEntity<String> deleteMarket(@RequestParam Integer marketId) throws Exception {
		return getResponseEntity(marketService.deleteMarket(marketId), "Market Delete");
	}
	
}
