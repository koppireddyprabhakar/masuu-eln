package com.ectd.global.eln.services;
import java.util.List;

import com.ectd.global.eln.dto.AnalysisDto;
import com.ectd.global.eln.dto.CoaReviewDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.TestRequestFormDto;

public interface PdfGenerationService {
	byte[] generatePdf(List<ExperimentDto> experiments, List<TestRequestFormDto> testRequests,
			List<CoaReviewDto> coaReviewDetails) throws Exception;

	byte[] generatePdfForAnalysis(List<AnalysisDto> analysisExperiments, List<TestRequestFormDto> testRequests,
			 List<CoaReviewDto> coaReviewDetails) throws Exception;
	
}
