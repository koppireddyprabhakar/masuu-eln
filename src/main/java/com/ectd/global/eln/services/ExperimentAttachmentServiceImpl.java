package com.ectd.global.eln.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ectd.global.eln.dao.ExperimentAttachmentDao;
import com.ectd.global.eln.dto.ExperimentAttachmentDto;
import com.ectd.global.eln.request.ExperimentAttachment;

@Service
public class ExperimentAttachmentServiceImpl implements ExperimentAttachmentService {
	
	@Autowired
	private ExperimentAttachmentDao expermentAttachmentDao;

	@Override
	public ExperimentAttachmentDto getExperimentAttachmentById(Integer experimentAttachmentId) {
		return expermentAttachmentDao.getExperimentAttachmentById(experimentAttachmentId);
	}

	@Override
	public List<ExperimentAttachmentDto> getExperimentAttachments() {
		return expermentAttachmentDao.getExperimentAttachments();
	}

	@Override
	public Integer createExperimentAttachment(ExperimentAttachment experimentAttachment) {
		return expermentAttachmentDao.createExperimentAttachment(experimentAttachment);
	}

	@Override
	public Integer updateExperimentAttachment(ExperimentAttachment experimentAttachment) {
		return expermentAttachmentDao.updateExperimentAttachment(experimentAttachment);
	}

	@Override
	public Integer deleteExperimentAttachment(ExperimentAttachment experimentAttachment) {
		return expermentAttachmentDao.deleteExperimentAttachment(experimentAttachment);
	}

}
