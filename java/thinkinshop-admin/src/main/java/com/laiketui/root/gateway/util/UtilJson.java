package com.laiketui.root.gateway.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class UtilJson {
	public static final ObjectMapper JSON_MAPPER = newObjectMapper(), JSON_MAPPER_WEB = newObjectMapper();

	private static ObjectMapper newObjectMapper() {
		ObjectMapper result = new ObjectMapper();
		result.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		result.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		result.setSerializationInclusion(Include.NON_NULL);
		result.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);	//不输出value=null的属性
		result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		return result;
	}

	public static ObjectMapper getObjectMapper() {
		return JSON_MAPPER;
	}
	
	public static String writeValueAsString(Object value) {
		try {
			return value == null ? null : JSON_MAPPER.writeValueAsString(value);
		} catch (IOException e) {
			throw new IllegalArgumentException(e); // TIP: 原则上，不对异常包装，这里为什么要包装？因为正常情况不会发生IOException
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object value) throws IllegalArgumentException {
		return convertValue(value, Map.class);
	}

	public static <T> T convertValue(Object value, Class<T> clazz) throws IllegalArgumentException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			if (value instanceof String) {
				value = JSON_MAPPER.readTree((String) value);
			}
			return JSON_MAPPER.convertValue(value, clazz);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	
	
}
