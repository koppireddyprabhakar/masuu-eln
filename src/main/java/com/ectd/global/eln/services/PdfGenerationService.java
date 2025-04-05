package com.ectd.global.eln.services;
import java.util.List;

import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.TestRequestFormDto;

public interface PdfGenerationService {
	  byte[] generatePdf(List<ExperimentDto> experiments, List<TestRequestFormDto> testRequests) throws Exception;
}
