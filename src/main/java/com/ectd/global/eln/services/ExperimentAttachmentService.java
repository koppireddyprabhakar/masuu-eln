package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.core.io.Resource;

import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.request.ExperimentAttachment;
import com.ectd.global.eln.request.FileInfo;

public interface ExperimentAttachmentService {

	ExperimentAttachmentDto getExperimentAttachmentById(Integer experimentId);

	List<ExperimentAttachmentDto> getExperimentAttachments();

	List<FileInfo> createExperimentAttachment(ExperimentAttachment experimentAttachment);

	Integer updateExperimentAttachment(ExperimentAttachment experimentAttachment);

	Boolean deleteExperimentAttachment(ExperimentAttachment experimentAttachment);
	
	List<FileInfo> getExperimentAttachments(Integer experimentId);
	
	Resource getExperimentAttachmentContent(String filename, Integer experimentId, Integer projectId);
	
}
