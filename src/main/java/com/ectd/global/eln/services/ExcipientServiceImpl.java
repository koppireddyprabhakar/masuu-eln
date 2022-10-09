package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ectd.global.eln.dao.ExcipientDao;
import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.request.ExcipientRequest;

@Service
public class ExcipientServiceImpl implements ExcipientService {

	@Autowired
	ExcipientDao excipientDao; 
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExcipientDto getExcipientById(Integer excipientId) {
		return excipientDao.getExcipientById(excipientId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExcipientDto> getExcipients() {
		return excipientDao.getExcipients();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExcipient(ExcipientRequest excipientRequest) {
		return excipientDao.createExcipient(excipientRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExcipient(ExcipientRequest excipientRequest) {
		return excipientDao.updateExcipient(excipientRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExcipient(Integer excipientId) {
		return excipientDao.deleteExcipient(excipientId);
	}

}
