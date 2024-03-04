package com.ectd.global.eln.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ectd.global.eln.dao.ExcipientDao;
import com.ectd.global.eln.dao.ExperimentDao;
import com.ectd.global.eln.dao.ProjectDao;
import com.ectd.global.eln.dao.UsersDetailsDao;
import com.ectd.global.eln.dto.ExcipientDto;
import com.ectd.global.eln.dto.ExperimentDto;
import com.ectd.global.eln.dto.ExperimentExcipientDto;
import com.ectd.global.eln.dto.ExperimentReviewDto;
import com.ectd.global.eln.dto.TestRequestFormDto;
import com.ectd.global.eln.dto.UsersDetailsDto;
import com.ectd.global.eln.exception.MaterialQuantityException;
import com.ectd.global.eln.request.EmailNotification;
import com.ectd.global.eln.request.ExcipientRequest;
import com.ectd.global.eln.request.ExperimentDetails;
import com.ectd.global.eln.request.ExperimentRequest;
import com.ectd.global.eln.request.ExperimentReview;
import com.ectd.global.eln.request.ProjectRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Service
public class ExperimentServiceImpl implements ExperimentService {

	@Autowired
	ExperimentDao experimentDao;
	
	@Autowired
	private UsersDetailsDao usersDetailsDao;
	
	@Autowired
    private ElnUtils elnUtils;
	
	@Autowired
    private EmailNotificationService emailNotificationService;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ExcipientDao excipientDao; 

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentDto getExperimentById(Integer experimentId) {
		return experimentDao.getExperimentById(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperiments(Integer userId, String status) {
		return experimentDao.getExperiments(userId, status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createExperiment(ExperimentRequest experimentRequest) {

		Integer experimentId = experimentDao.createExperiment(experimentRequest);
		
		 UsersDetailsDto creatorDetails = usersDetailsDao.getUsersDetailsById(experimentRequest.getUserId());
	     String creatorMailId = creatorDetails.getMailId();


		if(CollectionUtils.isEmpty(experimentRequest.getExperimentDetailsList())) {
			
			List<ExperimentDetails> experimentDetailsList = new ArrayList<ExperimentDetails>();
			
			ExperimentDetails experimentDetails = new ExperimentDetails();
			experimentDetails.setExperimentId(experimentId);
			experimentDetails.setName("Purpose and Conclusion");
			experimentDetails.setFileContent("");
			experimentDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
			experimentDetailsList.add(experimentDetails);
			
			experimentDetails = new ExperimentDetails();
			experimentDetails.setExperimentId(experimentId);
			experimentDetails.setName("Formulation");
			experimentDetails.setFileContent("");
			experimentDetails.setStatus(ElnUtils.STATUS.ACTIVE.getValue());
			experimentDetailsList.add(experimentDetails);
			
			experimentRequest.setExperimentDetailsList(experimentDetailsList);
			
			experimentDao.batchInsert(experimentRequest.getExperimentDetailsList());
		}
	       List<String> teamMemberMailIds = new ArrayList<String>();
	       EmailNotification emailNotification = elnUtils.buildEmailNotification("Formulation Experiment Created", "Formulation Experiment with EXP_ID " + experimentId + "   has been created successfully.", creatorMailId, teamMemberMailIds);
	       emailNotificationService.saveEmailNotification(emailNotification);
	       
	       ProjectRequest projectRequest = new ProjectRequest();
	       projectRequest.setProjectId(experimentRequest.getProjectId());
	       projectRequest.setStatus(ProjectRequest.PROJECT_STATUS.INPROGRESS.getValue());
	       projectDao.updateProjectStatus(projectRequest);
	       
//		if(!CollectionUtils.isEmpty(experimentRequest.getExcipients())) {
//			experimentRequest.getExcipients().stream().forEach(e -> e.setExperimentId(expermentId));
//			experimentDao.batchExcipientInsert(experimentRequest.getExcipients());
//		}

		return experimentId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer saveExcipient(List<ExcipientRequest> excipientRequests)  {

		if(CollectionUtils.isEmpty(excipientRequests)) {
			return 0;
		}
		experimentDao.deleteExperimentExcipient(excipientRequests.get(0).getExperimentId());

		List<Integer> ids = excipientRequests.stream().map(ExcipientRequest::getExcipientId).collect(Collectors.toList());
		List<ExcipientDto> excipientDtos= excipientDao.getExcipients(ids);
		
		List<ExcipientRequest> excipientRequestList = new ArrayList<ExcipientRequest>();

		synchronized (this) {
			for(ExcipientDto excipientDto: excipientDtos) {

				Optional<ExcipientRequest> excipientRequestOption =	excipientRequests.stream().filter(
						r -> (r.getExcipientId().equals(excipientDto.getExcipientId()) &&
						excipientDto.getRemainingQuantity() >= r.getChangedQuantity())
						).findFirst();

				if(excipientRequestOption.isEmpty()) {
					throw new MaterialQuantityException();
				}else {
					excipientDto.setRemainingQuantity(excipientDto.getRemainingQuantity() - excipientRequestOption.get().getChangedQuantity());
					excipientRequestList.add(excipientRequestOption.get());
					
					excipientDao.updateExcipient(mapFrom(excipientDto));
				}

			}
		}
		if(CollectionUtils.isEmpty(excipientRequestList)) {
			return 0;
		}

		return	experimentDao.batchExcipientInsert(excipientRequestList);
	}
	
	private ExcipientRequest mapFrom(ExcipientDto excipientDto) {
		ExcipientRequest request = new ExcipientRequest();
		
		request.setExcipientId(excipientDto.getExcipientId());
		request.setExcipientsName(excipientDto.getExcipientsName());
		request.setMaterialType(excipientDto.getMaterialType());
		request.setMaterialName(excipientDto.getMaterialName());
		request.setBatchNo(excipientDto.getBatchNo());
		request.setSourceName(excipientDto.getSourceName());
		request.setPotency(excipientDto.getPotency());
		request.setGrade(excipientDto.getGrade());
		request.setStatus(excipientDto.getStatus());
		request.setCreationSource(excipientDto.getCreationSource());
		request.setQuantity(excipientDto.getQuantity());
		request.setExpiryDate(excipientDto.getExpiryDate());
		request.setUpdateUser(excipientDto.getUpdateUser());
		request.setRemainingQuantity(excipientDto.getRemainingQuantity());
		
		return request;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer updateExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Integer deleteExperiment(ExperimentRequest experimentRequest) {
		return this.update(experimentRequest);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsWithProject() {
		return experimentDao.getExperimentsWithProject();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentDto> getExperimentsInfo(Integer experimentId) {
		return experimentDao.getExperimentsInfo(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateExperimentStatus(Integer experimentId, String status) {
		return experimentDao.updateExperimentStatus(experimentId, status);
	}

	private Integer update(ExperimentRequest experimentRequest) {

		experimentDao.updateExperiment(experimentRequest);
		
		if(!CollectionUtils.isEmpty(experimentRequest.getExperimentDetailsList())) {
		experimentDao.batchUpdate(experimentRequest.getExperimentDetailsList());
		}
		if(!CollectionUtils.isEmpty(experimentRequest.getExcipients())) {
		experimentDao.batchExcipientUpdate(experimentRequest.getExcipients());
		}

		return 1;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExperimentExcipientDto> getExcipientByExperimentId(Integer experimentId) {
		return experimentDao.getExcipientByExperimentId(experimentId);
	}

	@Override
	public Integer createExperimentReview(ExperimentReview experimentReview) {
		experimentDao.createExperimentReview(experimentReview);
		Integer experimentId = experimentReview.getExperimentId();
		this.updateExperimentStatus(experimentId, ExperimentRequest.EXPERIMENT_STATUS.INREVIEW.getValue());

		 UsersDetailsDto creatorDetails = usersDetailsDao.getUsersDetailsById(experimentReview.getReviewUserId());
	     String creatorMailId = creatorDetails.getMailId();


		// Send email notification to creator and team members
		List<String> teamMemberMailIds = new ArrayList<String>();
		  EmailNotification emailNotification = elnUtils.buildEmailNotification("Experiment Review Created","An experiment with experiment ID " + experimentId + "  has been sent for your review", creatorMailId,teamMemberMailIds);
		  emailNotificationService.saveEmailNotification(emailNotification);
		return experimentId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Integer updateExperimentReview(ExperimentReview experimentReview) {
		experimentDao.updateExperimentReview(experimentReview);

		return this.updateExperimentStatus(experimentReview.getExperimentId(), experimentReview.getStatus());
//		return this.updateExperimentStatus(experimentReview.getExperimentId(), ExperimentRequest.EXPERIMENT_STATUS.REVIEW_COMPLETED.getValue());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ExperimentReviewDto getExperimentReview(Integer experimentId) {
		return experimentDao.getExperimentReview(experimentId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<TestRequestFormDto> getTRFByExpIds(Integer experimentId) {
		return experimentDao.getTRFByExpIds(experimentId);
	}

}
