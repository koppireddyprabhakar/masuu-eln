package com.ectd.global.eln.services;

public interface SummaryPdfGenerationService {
	byte[] generateSummaryPdf(int projectId) throws Exception;
}
