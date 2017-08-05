package com.nexusdevs.shoppersdeal.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexusdevs.shoppersdeal.dto.Address;
import com.nexusdevs.shoppersdeal.dto.User;

@SuppressWarnings({"unchecked"})
public class JsonUtils {
	
	public static JSONObject errorResponse(String error) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("data", error);
		return jsonObject;
	}
	
	public static JSONObject successResponse(String success) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("data", success);
		return jsonObject;
	}
	
	public static Address getAddressFromJson(Address add) {
		if(add == null)
			return null;
		
		Address address = new Address();
		address.setStrtAdd1(add.getStrtAdd1());
		address.setStrtAdd2(add.getStrtAdd2());
		address.setCity(add.getCity());
		address.setState(add.getState());
		address.setCountry(add.getCountry());
		address.setLandmark(add.getLandmark());
		address.setAddType(add.getAddType());
		address.setZipcode(add.getZipcode());
		address.setCreatedTime(add.getCreatedTime());
		address.setUpdatedTime(add.getUpdatedTime());
		
		return address;
	}
	
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
			user.setAddress(((List<Address>)jsonObject.get("address")).stream().map(address -> getAddressFromJson(address)).collect(Collectors.toList()));
		}
		
		user.setCity((String)jsonObject.get("city"));
		user.setState((String)jsonObject.get("state"));
		user.setCountry((String)jsonObject.get("country"));
		user.setMailSubscription((Boolean)jsonObject.get("mailSubscription"));
		user.setCreateTime(((Long)jsonObject.get("createTime")).longValue());
		user.setUpdateTime(((Long)jsonObject.get("updateTime")).longValue());
		
		return user;
	}
}