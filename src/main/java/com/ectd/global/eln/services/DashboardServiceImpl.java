package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.DashboardDao;
import com.ectd.global.eln.dto.MonthCountDto;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private DashboardDao dashboardDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<MonthCountDto> getProjectCountsByMonth() {
		return dashboardDao.getProjectCountsByMonth();
	}

	@Override
	public List<MonthCountDto> getExperimentCountsByMonth() {
		return dashboardDao.getExperimentCountsByMonth();
	}

	@Override
	public List<MonthCountDto> getAnalysisExpByMonth() {
		return dashboardDao.getAnalysisExpByMonth();
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MonthCountDto> getExperimentByStatus() {
        return dashboardDao.getExperimentByStatus    ();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<MonthCountDto> getTRFByStatus() {
        return dashboardDao.getTRFByStatus    ();
    }

}
