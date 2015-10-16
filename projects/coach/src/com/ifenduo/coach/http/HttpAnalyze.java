package com.ifenduo.coach.http;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpAnalyze {

	static Gson gson;

	public HttpAnalyze() {
		super();
		gson = new GsonBuilder().serializeNulls().create();
	}

	public <T> BaseResponse<T> phoneAnalyze(String result, Type t) throws Exception {
		BaseResponse<T> response;
		try {
			response = gson.fromJson(result, t);
		} catch (Exception e) {
			response = null;
			throw e;
		}
		return response;
	}

	public <T> T analyze(String result, Type t) throws Exception {
		T response;
		try {
			response = gson.fromJson(result, t);
		} catch (Exception e) {
			response = null;
			throw e;
		}
		return response;
	}

	public String getString(Object obj, Type t) {
		return gson.toJson(obj, t);
	}
}