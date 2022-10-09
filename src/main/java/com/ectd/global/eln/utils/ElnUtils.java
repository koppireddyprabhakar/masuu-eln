package com.ectd.global.eln.utils;

import java.sql.Timestamp;

public class ElnUtils {
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

}
