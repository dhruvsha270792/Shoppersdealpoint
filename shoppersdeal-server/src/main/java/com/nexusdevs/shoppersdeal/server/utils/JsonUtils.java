package com.nexusdevs.shoppersdeal.server.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonUtils {
	
	public static JsonObject errorResponse(String error) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("success", false);
		jsonObject.addProperty("data", error);
		return jsonObject;
	}
	
	public static JsonObject successResponse(String success) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("success", true);
		jsonObject.addProperty("data", success);
		return jsonObject;
	}
	
	public static JsonObject createPaginatedResponse(JsonArray data, int total, int pos) {
		JsonObject resObj = new JsonObject();
		resObj.addProperty("total", total);
		if (data == null) {
			data = new JsonArray();
		}
		resObj.addProperty("n", data.size());
		resObj.addProperty("pos", pos);
		resObj.add("data", data);
		return resObj;
	}
}