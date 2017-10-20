package com.nexusdevs.shoppersdeal.server.utils;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<JsonObject> mergeById(JsonArray jsonArray1, JsonArray jsonArray2) {
		List<JsonObject> menuList = new ArrayList<>();
		
		for (int i=0; i < jsonArray1.size(); i++) {
			JsonObject jsonMap = new JsonObject();
			jsonMap.addProperty("id", jsonArray1.get(i).getAsJsonObject().get("id").getAsString());
			jsonMap.addProperty("categoryName", jsonArray1.get(i).getAsJsonObject().get("categoryName").getAsString());
			jsonMap.add("subcategory", new JsonArray());
			
			JsonArray jsonArray = new JsonArray();
			for (int j=0; j < jsonArray2.size(); j++) {
				if(jsonMap.get("id").toString().equals(jsonArray2.get(j).getAsJsonObject().get("categoryId").toString())) {
					JsonObject jsonObject = new JsonObject();
					jsonObject.addProperty("id", jsonArray2.get(j).getAsJsonObject().get("id").getAsString());
					jsonObject.addProperty("subcategoryName", jsonArray2.get(j).getAsJsonObject().get("subcategoryName").getAsString());
					jsonArray.add(jsonObject);
				}
			}
			
			jsonMap.add("subcategory", jsonArray);
			menuList.add(jsonMap);
		}
		return menuList;
	}
}