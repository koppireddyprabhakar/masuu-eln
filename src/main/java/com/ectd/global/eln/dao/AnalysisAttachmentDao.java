package com.ectd.global.eln.dao;

import java.util.List;

import com.ectd.global.eln.dto.AnalysisAttachmentDto;
import com.ectd.global.eln.request.AnalysisAttachment;

public interface AnalysisAttachmentDao {

	AnalysisAttachmentDto getAnalysisAttachmentById(Integer analysisExperimentId);

	List<AnalysisAttachmentDto> getAnalysisAttachments(Integer experimentId, String fileName);

	Integer createAnalysisAttachment(AnalysisAttachment analysisAttachment);

	Integer updateAnalysisAttachment(AnalysisAttachment analysisAttachment);

	Integer deleteAnalysisAttachment(AnalysisAttachment analysisAttachment);
	
}
