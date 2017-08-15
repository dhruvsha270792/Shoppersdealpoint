package com.nexusdevs.shoppersdeal.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.nexusdevs.shoppersdeal.db.MongoDBManager;
import com.nexusdevs.shoppersdeal.dto.User;
import com.nexusdevs.shoppersdeal.dto.UserSession;
import com.nexusdevs.shoppersdeal.utils.JsonUtils;

@Service
@SuppressWarnings({ "unused", "unchecked" })
public class DAOService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static String USER_COLLECTION = "users";
	private static String USER_SESSION_COLLECTION = "user_session";

	@Autowired
	private MongoDBManager mongoDBManager;

	/*//get Json From Document
	public JSONObject getJsonFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		JSONObject jsonObject = (JSONObject) JSONValue.parse(dbObj.toString());
		if (idObj != null) {
			String id = idObj.toString();
			jsonObject.put("id", id);
		}
		return jsonObject;
	}*/

	//get User From Document
	public User getUserFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		JSONObject jsonObject = (JSONObject) JSONValue.parse(dbObj.toString());
		if (idObj != null) {
			String id = idObj.toString();
			jsonObject.put("id", id);
			jsonObject.remove("passcode");
		}
		User user = JsonUtils.fromJsonToUser(jsonObject);
		return user;
	}
	
	//create new user
	public String createUser(User user) {
		JSONObject jsonObject = JsonUtils.toJson(user);
		jsonObject.remove("id");
		String addObject = mongoDBManager.addObject(USER_COLLECTION, jsonObject.toString());
		return addObject;
	}

	public User getUserDetail(String emailId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("email", emailId);
		Document document = mongoDBManager.getObject(USER_COLLECTION, queryParams);
		if (document == null)
			return null;
		User user = getUserFromDocument(document);
		return user;
	}

	public User login(String email, String pass) {
		try {
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("email", email);
			queryParams.put("passcode", pass);
			Document document = mongoDBManager.getObject(USER_COLLECTION, queryParams);
			if (document != null)
				return getUserFromDocument(document);
		}
		catch (Exception e) {}
		return null;
	}
	
	public void insertSession(UserSession session) {
		JSONObject jsonObject = JsonUtils.toJson(session);
		mongoDBManager.addObject(USER_SESSION_COLLECTION, jsonObject.toString());
	}
	
	public void logout(UserSession userSession) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("token", userSession.getToken());
		Map<String, Object> fieldValueMap = new HashMap<>();
		fieldValueMap.put("expired", true);
		mongoDBManager.setField(USER_SESSION_COLLECTION, queryParams, fieldValueMap, true, true);
	}
}