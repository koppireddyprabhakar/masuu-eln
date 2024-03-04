package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
	public List<ExcipientDto> getExcipients(String creationSource) {
		return excipientDao.getExcipients(creationSource);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExcipient(ExcipientRequest excipientRequest) {
		return excipientDao.createExcipient(excipientRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExcipient(ExcipientRequest excipientRequest) {
		
		if(excipientRequest.getQuantity().doubleValue() == 0.00) {
			excipientRequest.setQuantity(excipientRequest.getChangedQuantity());
			excipientRequest.setRemainingQuantity(excipientRequest.getChangedQuantity());
		}else if(excipientRequest.getChangedQuantity().doubleValue() <= excipientRequest.getQuantity().doubleValue()) {
			Double difference = excipientRequest.getQuantity().doubleValue() - excipientRequest.getChangedQuantity().doubleValue();
			excipientRequest.setRemainingQuantity(excipientRequest.getRemainingQuantity().doubleValue() - difference);
			excipientRequest.setQuantity(excipientRequest.getChangedQuantity());
		}else if(excipientRequest.getChangedQuantity().doubleValue() >= excipientRequest.getQuantity().doubleValue()) {
			Double difference = excipientRequest.getChangedQuantity().doubleValue() - excipientRequest.getQuantity().doubleValue();
			excipientRequest.setRemainingQuantity(excipientRequest.getRemainingQuantity().doubleValue() + difference);
			excipientRequest.setQuantity(excipientRequest.getChangedQuantity());
		}
		
		
		return excipientDao.updateExcipient(excipientRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExcipient(Integer excipientId) {
		ExcipientDto excipientDto = excipientDao.getExcipientById(excipientId);
		ExcipientRequest excipientRequest = new ExcipientRequest();
		BeanUtils.copyProperties(excipientDto, excipientRequest);
		excipientRequest.setStatus("Inactive");
		return excipientDao.updateExcipient(excipientRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExcipientDto> getExcipientsByMaterialName(String materialName) {
		return excipientDao.getExcipientsByMaterialName(materialName);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveExcipient(List<ExcipientRequest> excipientRequests) {
		
		excipientRequests.stream().forEach(e -> {
			if(e.getExcipientId() == null) {
				this.createExcipient(e);
			} else {
				this.updateExcipient(e);
			}
		});
		
		return 1;
	}

}
