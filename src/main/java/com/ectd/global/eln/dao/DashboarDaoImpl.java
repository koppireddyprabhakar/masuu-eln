package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ectd.global.eln.dto.MonthCountDto;

@Repository
@PropertySource(value = { "classpath:sql/dashboard-dao.properties" })
public class DashboarDaoImpl implements DashboardDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${getProjectsCountQuery}")
	private String GET_PROJECT_COUNTS_QUERY;

	@Value("${getExperimentsCountQuery}")
	private String GET_EXPERIMENT_COUNTS_QUERY;

	@Value("${getAnalysisExpCountQuery}")
	private String GET_ANALYSISEXP_COUNTS_QUERY;
	
	@Value("${getExperimentByStatusQuery}")
    private String GET_EXP_BY_STATUS_QUERY;

    @Value("${getTrfByStatusQuery}")
    private String GET_TRF_BY_STATUS_QUERY;

	@Override
	public List<MonthCountDto> getProjectCountsByMonth() {
		return jdbcTemplate.query(GET_PROJECT_COUNTS_QUERY, new MonthCountRowMapper());
	}

	@Override
	public List<MonthCountDto> getExperimentCountsByMonth() {
		return jdbcTemplate.query(GET_EXPERIMENT_COUNTS_QUERY, new MonthCountRowMapper());
	}

	@Override
	public List<MonthCountDto> getAnalysisExpByMonth() {
		return jdbcTemplate.query(GET_ANALYSISEXP_COUNTS_QUERY, new MonthCountRowMapper());
	}

	public class MonthCountRowMapper implements RowMapper<MonthCountDto> {
		@Override
		public MonthCountDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			MonthCountDto monthCountDto = new MonthCountDto();
			monthCountDto.setCount(resultSet.getInt("total_count"));
			return monthCountDto;
		}
	}
	
	@Override
    public List<MonthCountDto> getExperimentByStatus() {
        return jdbcTemplate.query(GET_EXP_BY_STATUS_QUERY, new MonthCountRowMapper());
    }

    @Override
    public List<MonthCountDto> getTRFByStatus() {
        return jdbcTemplate.query(GET_TRF_BY_STATUS_QUERY, new MonthCountRowMapper());
    }
}
