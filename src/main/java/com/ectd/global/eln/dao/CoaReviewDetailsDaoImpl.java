package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.CoaReviewDto;
import com.ectd.global.eln.request.CoaReviewDetailsRequest;
import com.ectd.global.eln.utils.ElnUtils;


@Repository
@PropertySource(value = { "classpath:sql/users-details-dao.properties" })
public class CoaReviewDetailsDaoImpl implements CoaReviewDetailsDao{
	
	   @Autowired
		@Qualifier("jdbcTemplate")
		private JdbcTemplate jdbcTemplate;
	 
		@Autowired
		@Qualifier("namedParameterJdbcTemplate")
		private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		
		    @Value("${insertcoareviewDetails}")
		    private String COA_REVIEW_DETAILS;
	 
		    @Value("${getCoaReviewDetailsByExpId}")
		    private String GET_COA_REVIEW_DETAILS_BY_EXP_ID;
		   
	 
		    @Value("${updatecoareviewDetails}")
		    private String  UPDATE_COA_REVIEW_QUERY;
		    
		    @Value("${updatecoaaprovalDetails}")
		    private String  UPDATE_COA_APROVAL_QUERY;
		    
		    @Value("${updatecoaanalysisreview}")
		    private String  UPDATE_COA_ANALYSIS_REVIEW_QUERY;
		    
		    @Value("${updatecoaanalysisaproval}")
		    private String  UPDATE_COA_ANALYSIS_APROVAL_QUERY;
		    
		    @Value("${getCoaReviewDetailsByanalysisId}")
		    private String GET_COA_REVIEW_DETAILS_BY_ANALYSIS_ID;
		    
		    @Override
		    public List<CoaReviewDto> getCoaReviewDetails(Integer experimentId) {
		        MapSqlParameterSource parameters = new MapSqlParameterSource();
	 
		        if (experimentId != null) {
		            parameters.addValue("experimentId", experimentId);
		            return namedParameterJdbcTemplate.query(GET_COA_REVIEW_DETAILS_BY_EXP_ID, parameters, new CoaReviewRowMapper());
		        } else {
		            return Collections.emptyList();
		        }
		    }
		    
		    @Override
		    public List<CoaReviewDto> getCoaReviewDetailsForAnalysis(Integer analysisId) {
		        MapSqlParameterSource parameters = new MapSqlParameterSource();
	 
		        if (analysisId != null) {
		            parameters.addValue("analysisId", analysisId);
		            return namedParameterJdbcTemplate.query(GET_COA_REVIEW_DETAILS_BY_ANALYSIS_ID, parameters, new CoaReviewRowMapper());
		        } else {
		            return Collections.emptyList();
		        }
		    }
		    
		    /*
		    @Override
		    public Integer saveCoaCreateDetails(CoaReviewDetailsRequest coaDetailsRequest) {
		        MapSqlParameterSource parameters = new MapSqlParameterSource();	       
		        parameters.addValue("expId", coaDetailsRequest.getExperimentId());
		        parameters.addValue("analysisExpId", coaDetailsRequest.getAnalysisExpId());
		        parameters.addValue("preparedByUserId", coaDetailsRequest.getPreparedByUserId());
		        parameters.addValue("reviewedByUserId", coaDetailsRequest.getReviewedByUserId());
		        parameters.addValue("approvedByUserId", coaDetailsRequest.getApprovedByUserId());
		        parameters.addValue("preparedByDate", coaDetailsRequest.getPreparedByDate());
		        parameters.addValue("reviewedByDate", coaDetailsRequest.getReviewedByDate());
		        parameters.addValue("approvedByDate", coaDetailsRequest.getApprovedByDate());
		        parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		        parameters.addValue("updateUser", coaDetailsRequest.getPreparedByUserId());
		        parameters.addValue("updateDate", ElnUtils.getTimeStamp());
	 
		        return namedParameterJdbcTemplate.update(COA_REVIEW_DETAILS, parameters);
		    }
		    */
		    
		    @Override
		    public Integer saveCoaCreateDetails(CoaReviewDetailsRequest coaDetailsRequest) {
		        MapSqlParameterSource parameters = new MapSqlParameterSource();	       
		        parameters.addValue("expId", coaDetailsRequest.getExperimentId());
		        parameters.addValue("analysisExpId", coaDetailsRequest.getAnalysisExpId());
		        parameters.addValue("preparedByUserId", coaDetailsRequest.getPreparedByUserId());
		        parameters.addValue("reviewedByUserId", coaDetailsRequest.getReviewedByUserId());
		        parameters.addValue("approvedByUserId", coaDetailsRequest.getApprovedByUserId());
		        parameters.addValue("preparedByDate", coaDetailsRequest.getPreparedByDate());
		        parameters.addValue("reviewedByDate", coaDetailsRequest.getReviewedByDate());
		        parameters.addValue("approvedByDate", coaDetailsRequest.getApprovedByDate());
		        parameters.addValue("insertDate", ElnUtils.getTimeStamp());
		        parameters.addValue("updateUser", coaDetailsRequest.getPreparedByUserId());
		        parameters.addValue("updateDate", ElnUtils.getTimeStamp());
		        parameters.addValue("complianceStatus", coaDetailsRequest.isComplianceStatus() ? 1 : 0);
		        return namedParameterJdbcTemplate.update(COA_REVIEW_DETAILS, parameters);
		    }
	 
		    
		    @Override
			public Integer updateCoaFormulationReview(CoaReviewDetailsRequest coaDetailsRequest) {
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				 parameters.addValue("expId", coaDetailsRequest.getExperimentId());
			        parameters.addValue("reviewedByUserId", coaDetailsRequest.getReviewedByUserId());
			        parameters.addValue("reviewedByDate", coaDetailsRequest.getReviewedByDate());
				return namedParameterJdbcTemplate.update(UPDATE_COA_REVIEW_QUERY, parameters);
			}
		    
		    @Override
			public Integer updateCoaFormulationAproval(CoaReviewDetailsRequest coaDetailsRequest) {
				MapSqlParameterSource parameters = new MapSqlParameterSource();
				 parameters.addValue("expId", coaDetailsRequest.getExperimentId());
				    parameters.addValue("approvedByUserId", coaDetailsRequest.getApprovedByUserId());
				    parameters.addValue("approvedByDate", coaDetailsRequest.getApprovedByDate());
				return namedParameterJdbcTemplate.update(UPDATE_COA_APROVAL_QUERY, parameters);
			}
		    
		    @Override
			public Integer updateAnalysisCoaReview(CoaReviewDetailsRequest coaDetailsRequest) {
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			    parameters.addValue("analysisExpId", coaDetailsRequest.getAnalysisExpId());
			        parameters.addValue("reviewedByUserId", coaDetailsRequest.getReviewedByUserId());
			        parameters.addValue("reviewedByDate", coaDetailsRequest.getReviewedByDate());
				return namedParameterJdbcTemplate.update(UPDATE_COA_ANALYSIS_REVIEW_QUERY, parameters);
			}
		    
		    @Override
			public Integer updateAnalysisCoaAproval(CoaReviewDetailsRequest coaDetailsRequest) {
				MapSqlParameterSource parameters = new MapSqlParameterSource();
			    parameters.addValue("analysisExpId", coaDetailsRequest.getAnalysisExpId());
				    parameters.addValue("approvedByUserId", coaDetailsRequest.getApprovedByUserId());
				    parameters.addValue("approvedByDate", coaDetailsRequest.getApprovedByDate());
				return namedParameterJdbcTemplate.update(UPDATE_COA_ANALYSIS_APROVAL_QUERY, parameters);
			}
		    
		    
		public class CoaReviewRowMapper implements RowMapper<CoaReviewDto> {
		    @Override
		    public CoaReviewDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		        CoaReviewDto coaReviewDto = new CoaReviewDto();
		        coaReviewDto.setPreparedName(resultSet.getString("Prepared_Name"));
		        coaReviewDto.setPreparedDesignation(resultSet.getString("Prepared_Designation"));
		        coaReviewDto.setPreparedDate(resultSet.getTimestamp("Prepared_Date"));
		        coaReviewDto.setReviewerName(resultSet.getString("Reviewer_Name"));
		        coaReviewDto.setReviewerDesignation(resultSet.getString("Reviewer_Designation"));
		        coaReviewDto.setReviewedDate(resultSet.getTimestamp("Reviewed_Date"));
		        coaReviewDto.setApproverName(resultSet.getString("Approver_Name"));
		        coaReviewDto.setApproverDesignation(resultSet.getString("Approver_Designation"));
		        coaReviewDto.setApprovedDate(resultSet.getTimestamp("Approved_Date"));
		        coaReviewDto.setComplianceStatus(resultSet.getBoolean("COMPLIANCE_STATUS"));
		        return coaReviewDto;
		    }
		}
		
  }


