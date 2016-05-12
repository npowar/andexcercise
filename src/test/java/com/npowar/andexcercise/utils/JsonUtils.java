package com.npowar.andexcercise.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static <T> T convrtToObject(String json, Class<T> klass) {
		try {
			T obj = objectMapper.readValue(json, klass);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> String convertToString(T object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
