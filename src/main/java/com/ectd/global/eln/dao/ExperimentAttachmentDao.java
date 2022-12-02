package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.request.ExperimentAttachment;

public interface ExperimentAttachmentDao {
	
	ExperimentAttachmentDto getExperimentAttachmentById(Integer experimentId);

	List<ExperimentAttachmentDto> getExperimentAttachments();

	Integer createExperimentAttachment(ExperimentAttachment experimentAttachment);

	Integer updateExperimentAttachment(ExperimentAttachment experimentAttachment);

	Integer deleteExperimentAttachment(ExperimentAttachment experimentAttachment);

}
