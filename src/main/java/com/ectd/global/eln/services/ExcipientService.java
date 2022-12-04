package com.ectd.global.eln.services;

import java.util.List;

import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.request.ExcipientRequest;

public interface ExcipientService {

	ExcipientDto getExcipientById(Integer excipientId);

	List<ExcipientDto> getExcipients();
	
	Integer createExcipient(ExcipientRequest excipientRequest);

	Integer updateExcipient(ExcipientRequest excipientRequest);

	Integer deleteExcipient(Integer excipientId);

	List<ExcipientDto> getExcipientsByMaterialName(String materialName);
	
	Integer saveExcipient(List<ExcipientRequest> excipientRequests);
}
