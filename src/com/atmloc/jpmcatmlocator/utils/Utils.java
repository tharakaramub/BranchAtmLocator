package com.atmloc.jpmcatmlocator.utils;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static ObjectMapper m = new ObjectMapper();
	private static JsonFactory jf = new JsonFactory();

	public static <T> Object fromJson(String reponse, Class<T> pojo)
			throws JsonParseException, JsonMappingException, IOException {
		return m.readValue(reponse, pojo);
	}

	public static String toJson(Object pojo) throws JsonGenerationException,
			JsonMappingException, IOException {
		StringWriter writer = new StringWriter();
		JsonGenerator jg = jf.createJsonGenerator(writer);
		m.writeValue(jg, pojo);
		return writer.toString();
	}

}
