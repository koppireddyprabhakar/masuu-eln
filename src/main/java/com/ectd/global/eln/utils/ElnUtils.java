package com.ectd.global.eln.utils;

import java.sql.Timestamp;

public class ElnUtils {

	public static String DEFAULT_USER_ID = "ELN";
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static enum STATUS {
		ACTIVE("Active"), INACTIVE("Inactive");
		
		private String value;
		
		STATUS(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
}
