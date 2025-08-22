package com.ectd.global.eln.services;

public interface ProjectPdfGenerationService {
	
	 byte[] generateProjectPdf(Integer projectId) throws Exception;
	 
	 byte[] generateProjectExperimentsPdf(Integer projectId) throws Exception;

}
