package com.ectd.global.eln.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ectd.global.eln.dto.MonthCountDto;
import com.ectd.global.eln.services.DashboardService;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController extends BaseController {

	@Autowired
	DashboardService dashboardService;

	@GetMapping("/project-by-month")
	public ResponseEntity<Integer[]> getProjectsByMonth() {
		List<MonthCountDto> projectCounts = dashboardService.getProjectCountsByMonth();
		List<Integer> counts = projectCounts.stream().map(MonthCountDto::getCount).collect(Collectors.toList());
		Integer[] arr = counts.toArray(new Integer[0]);
		return ResponseEntity.ok(arr);
	}

	@GetMapping("/experiments-by-month")
	public ResponseEntity<Integer[]> getExperimentsByMonth() {
		List<MonthCountDto> experimentCounts = dashboardService.getExperimentCountsByMonth();
		List<Integer> counts = experimentCounts.stream().map(MonthCountDto::getCount).collect(Collectors.toList());
		Integer[] arr = counts.toArray(new Integer[0]);
		return ResponseEntity.ok(arr);
	}

	@GetMapping("/analysis-experiments-by-month")
	public ResponseEntity<Integer[]> getAnalysisExpByMonth() {
		List<MonthCountDto> analysisexperimentCounts = dashboardService.getAnalysisExpByMonth();
		List<Integer> counts = analysisexperimentCounts.stream().map(MonthCountDto::getCount).collect(Collectors.toList());
		Integer[] arr = counts.toArray(new Integer[0]);
		return ResponseEntity.ok(arr);
	}

	@GetMapping("/experiment-by-status")
	public ResponseEntity<Integer[]> getExperimentByStatus() {
		List<MonthCountDto> experimentCount = dashboardService.getExperimentByStatus();
		List<Integer> counts = experimentCount.stream().map(MonthCountDto::getCount).collect(Collectors.toList());
		Integer[] arr = counts.toArray(new Integer[0]);
		return ResponseEntity.ok(arr);
	}

	@GetMapping("/trf-by-status")
	public ResponseEntity<Integer[]> getTRFByStatus() {
		List<MonthCountDto> trfCount = dashboardService.getTRFByStatus();
		List<Integer> counts = trfCount.stream().map(MonthCountDto::getCount).collect(Collectors.toList());
		Integer[] arr = counts.toArray(new Integer[0]);
		return ResponseEntity.ok(arr);
	}
}