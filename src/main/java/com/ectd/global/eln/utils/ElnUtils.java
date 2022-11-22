package com.ectd.global.eln.utils;

import java.sql.Timestamp;

public class ElnUtils {

	public static String DEFAULT_USER_ID = "ELN";
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
}
