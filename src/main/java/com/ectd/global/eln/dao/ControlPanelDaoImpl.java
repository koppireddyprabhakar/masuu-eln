package com.ectd.global.eln.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.ectd.global.eln.dto.ControlPanelDto;
import com.ectd.global.eln.request.ControlPanelRequest;
import com.ectd.global.eln.utils.ElnUtils;

@Repository
@PropertySource(value = {"classpath:sql/control-panel-dao.properties"})
public class ControlPanelDaoImpl implements ControlPanelDao {

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Value(value="${createControlPanel}")
    private String createControlPanelQuery;

    @Value(value="${updateControlPanel}")
    private String updateControlPanelQuery;
    
    @Value(value="${getControlPanel}")
    private String getControlPanelQuery;
    
    @Value(value = "${getNumberOfUsers}")
    private String getNumberOfUsersQuery;



    @Override
    public ControlPanelDto getControlPanel() {
        MapSqlParameterSource parameters = new MapSqlParameterSource(); 
        try {
            return namedParameterJdbcTemplate.queryForObject(getControlPanelQuery, parameters, new ControlPanelRowMapper());
        } catch (EmptyResultDataAccessException e) {
           
            return null;
        }
    }



    @Override
    public Integer createControlPanel(ControlPanelRequest controlPanelRequest) {
       
        
        MapSqlParameterSource parameters = new MapSqlParameterSource();
      
        parameters.addValue("usersLimit", controlPanelRequest.getUsersLimit()); 
        parameters.addValue("licenceStartDate", controlPanelRequest.getLicenceStartDate());
        parameters.addValue("licenceExpiryDate", controlPanelRequest.getLicenceExpiryDate());
        
        return namedParameterJdbcTemplate.update(createControlPanelQuery, parameters);
    }


    @Override
    public Integer updateControlPanel(ControlPanelRequest controlPanelRequest) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();         
        parameters.addValue("usersLimit", controlPanelRequest.getUsersLimit());
        parameters.addValue("licenceStartDate", controlPanelRequest.getLicenceStartDate());
        parameters.addValue("licenceExpiryDate", controlPanelRequest.getLicenceExpiryDate());

        return namedParameterJdbcTemplate.update(updateControlPanelQuery, parameters);
    }

  

    class ControlPanelRowMapper implements RowMapper<ControlPanelDto> {
        public ControlPanelDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {           
            ControlPanelDto controlPanelDTO = new ControlPanelDto();     
            controlPanelDTO.setUsersLimit(resultSet.getInt("USERS_LIMIT"));                    
            controlPanelDTO.setLicenceStartDate(resultSet.getDate("LICENCE_START_DATE"));
            controlPanelDTO.setLicenceExpiryDate(resultSet.getDate("LICENCE_EXPIRY_DATE"));
           
            
            return controlPanelDTO;
        }
    }
    

@Override
public Integer getNumberOfUsers() {
    try {
        return jdbcTemplate.queryForObject(getNumberOfUsersQuery, Integer.class);
    } catch (EmptyResultDataAccessException e) {
        return 0; 
    }
}

}
