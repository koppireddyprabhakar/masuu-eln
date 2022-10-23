package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.request.ExcipientRequest;

public interface ExcipientDao {
	
	ExcipientDto getExcipientById(Integer excipientId);

	List<ExcipientDto> getExcipients();
	
	Integer createExcipient(ExcipientRequest excipientRequest);

	Integer updateExcipient(ExcipientRequest excipientRequest);

	Integer deleteExcipient(Integer excipientId);
	
	List<ExcipientDto> getExcipientsByMaterialName(String materialName);

}
