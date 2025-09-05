package com.ectd.global.eln.utils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

	 public static String getClientIp(HttpServletRequest request) {
	        String ip = request.getHeader("X-Forwarded-For");
	        if (ip != null && !ip.isEmpty()) {
	            // X-Forwarded-For may contain multiple IPs: "client, proxy1, proxy2"
	            return ip.split(",")[0].trim();
	        }
	        return request.getRemoteAddr();
	    }
	
}
