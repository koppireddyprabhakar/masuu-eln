package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.core.io.Resource;

import com.ectd.global.eln.dto.AnalysisAttachmentDto;
import com.ectd.global.eln.request.AnalysisAttachment;
import com.ectd.global.eln.request.FileInfo;

public interface AnalysisAttachmentService {
	
	AnalysisAttachmentDto getAnalysisAttachmentById(Integer analysisAttachmentId);

	List<AnalysisAttachmentDto> getAnalysisAttachments();
	
	List<FileInfo> createAnalysisAttachment(AnalysisAttachment analysisAttachment);

	Integer updateAnalysisAttachment(AnalysisAttachment analysisAttachment);

	Boolean deleteAnalysisAttachment(AnalysisAttachment analysisAttachment);
	
	List<FileInfo> getAnalysisAttachments(Integer experimentId);
	
	Resource getAnalysisAttachmentContent(String fileName, Integer experimentId, Integer projectId);

}
