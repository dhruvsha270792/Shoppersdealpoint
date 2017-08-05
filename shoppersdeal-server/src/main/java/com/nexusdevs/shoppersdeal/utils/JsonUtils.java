package com.nexusdevs.shoppersdeal.utils;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexusdevs.shoppersdeal.dto.Address;
import com.nexusdevs.shoppersdeal.dto.User;

public class JsonUtils {
	
	public static User fromJsonObjToUser(JSONObject jsonObject){
		User user = new User();
		user.setId((String)jsonObject.get("id"));
		user.setUserId((String)jsonObject.get("userId"));
		user.setName((String)jsonObject.get("name"));
		user.setAge(((Number)jsonObject.get("age")).intValue());
		user.setMobile(((Number)jsonObject.get("mobile")).longValue());
		user.setEmail((String)jsonObject.get("email"));
		user.setPasscode((String)jsonObject.get("passcode"));
		if(jsonObject.get("address") instanceof JSONArray) {
			((List<Address>)jsonObject.get("address")).stream().map(mapper)
		}
		return user;
	}
	
	public static void main(String[] args) {
		System.out.println(fromJsonObjToUser(jsonObject));
	}
}
