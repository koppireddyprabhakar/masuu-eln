package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.MonthCountDto;

public interface DashboardService {

	List<MonthCountDto> getProjectCountsByMonth();

	List<MonthCountDto> getExperimentCountsByMonth();

	List<MonthCountDto> getAnalysisExpByMonth();
	
	List<MonthCountDto> getExperimentByStatus();

    List<MonthCountDto> getTRFByStatus();

}
