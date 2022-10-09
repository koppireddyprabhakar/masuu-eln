package com.ectd.global.eln.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.log4j.*;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonBean {
	

	private static Logger log = LogManager.getLogger(CommonBean.class.getName());

	@Override
	public String toString() {

		String str = "";
		if (log.isDebugEnabled()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				str = mapper.writeValueAsString(this);
			} catch (Exception e) {
				log.error(e + "", e);
			}
		} else {
			str = super.toString();
		}
		return str;
	}
}
