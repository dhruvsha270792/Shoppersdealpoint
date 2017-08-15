package com.nexusdevs.shoppersdeal.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.nexusdevs.shoppersdeal.dto.Address;
import com.nexusdevs.shoppersdeal.dto.User;
import com.nexusdevs.shoppersdeal.dto.UserSession;

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
	
	public static JSONObject toJson(Address add) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("strtAdd1", add.getStrtAdd1());
		jsonObject.put("strtAdd2", add.getStrtAdd2());
		jsonObject.put("city", add.getCity());
		jsonObject.put("state", add.getState());
		jsonObject.put("country", add.getCountry());
		jsonObject.put("zipcode", add.getZipcode());
		jsonObject.put("landmark", add.getLandmark());
		jsonObject.put("createTime", add.getCreatedTime());
		jsonObject.put("updateTime", add.getUpdatedTime());
		if(add.getAddType() != null)
			jsonObject.put("adType", add.getAddType());
		return jsonObject;
	}
	
	public static User fromJsonToUser(JSONObject jsonObject){
		User user = new User();
		if(jsonObject.get("id") instanceof String)
			user.setId((String)jsonObject.get("id"));
		
		if(jsonObject.get("userId") instanceof String)
			user.setUserId((String)jsonObject.get("userId"));
		
		if(jsonObject.get("name") instanceof String)
			user.setName((String)jsonObject.get("name"));
		
		if(jsonObject.get("age") instanceof Number)
			user.setAge(((Number)jsonObject.get("age")).intValue());
		
		if(jsonObject.get("mobile") instanceof Number)
			user.setMobile(((Number)jsonObject.get("mobile")).longValue());
		
		if(jsonObject.get("email") instanceof String)
			user.setEmail((String)jsonObject.get("email"));
		
		if(jsonObject.get("passcode") instanceof String)
			user.setPasscode((String)jsonObject.get("passcode"));
		
		if(jsonObject.get("address") instanceof JSONArray) {
			//user.setAddress(((List<Address>)jsonObject.get("address")).stream().map(address -> getAddressFromJson(address)).collect(Collectors.toList()));
		}
		if(jsonObject.get("mailSubscription") instanceof Boolean)
			user.setMailSubscription((Boolean)jsonObject.get("mailSubscription"));
		
		if(jsonObject.get("verified") instanceof Boolean)
			user.setPasscode((String)jsonObject.get("passcode"));
		
		if(jsonObject.get("blocked") instanceof Boolean)
			user.setPasscode((String)jsonObject.get("passcode"));
		
		if(jsonObject.get("createTime") instanceof Number)
			user.setCreateTime(((Long)jsonObject.get("createTime")).longValue());
		
		if(jsonObject.get("createTime") instanceof Number)
			user.setUpdateTime(((Long)jsonObject.get("updateTime")).longValue());
		
		return user;
	}
	
	public static JSONObject toJson(User user) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", user.getId());
		jsonObject.put("userId", user.getUserId());
		jsonObject.put("name", user.getName());
		jsonObject.put("age", user.getAge());
		jsonObject.put("mobile", user.getMobile());
		jsonObject.put("email", user.getEmail());
		jsonObject.put("passcode", user.getPasscode());
		if (user.getAddress() != null) {
			JSONArray arr = new JSONArray();
			user.getAddress().forEach(o -> arr.add(toJson(o)));
			jsonObject.put("address", arr);
		}
		jsonObject.put("mailSubscription", user.getMailSubscription());
		jsonObject.put("verified", user.getVerified());
		jsonObject.put("blocked", user.getBlocked());
		jsonObject.put("createTime", user.getCreateTime());
		jsonObject.put("updateTime", user.getUpdateTime());
		return jsonObject;
	}
	
	public static UserSession fromJsonToUserSession(JSONObject jsonObject) {
		UserSession userSession = new UserSession();
		if(jsonObject.get("userId") instanceof String)
			userSession.setUserId((String)jsonObject.get("userId"));
		
		if(jsonObject.get("token") instanceof String)
			userSession.setToken((String)jsonObject.get("token"));
		
		if(jsonObject.get("expired") instanceof Boolean)
			userSession.setExpired((boolean) jsonObject.get("expired"));
		
		if(jsonObject.get("createTime") instanceof Number)
			userSession.setCreateTime((Long)jsonObject.get("createTime"));
		
		if(jsonObject.get("validTill") instanceof Number)
			userSession.setValidTill((Long)jsonObject.get("validTill"));
		
		return userSession;
	}
	
	public static JSONObject toJson(UserSession session) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", session.getUserId());
		jsonObject.put("token", session.getToken());
		jsonObject.put("expired", session.isExpired());
		jsonObject.put("createTime", session.getCreateTime());
		jsonObject.put("validTill", session.getValidTill());
		return jsonObject;
	}
}