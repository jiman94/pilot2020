package oss.pilot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	private StringUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	
	public static boolean isNull(String str) {
		return str == null ? true : false;
	}
	
	public static String nullToString(String str) {
		return str == null ? "" : str;
	}
	
	public static boolean isEmpty(Object obj) {
		boolean result = false;
		if (obj == null) {
			result = true;
		}
		if (obj instanceof java.lang.String) {
			if (String.valueOf(obj).equals("") || String.valueOf(obj).equals("null")) {
				result = true;
			}
		}
		return result;
	}
	
	public static String jsonObjToString(Object obj) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
		}
		return result;
	}

}
