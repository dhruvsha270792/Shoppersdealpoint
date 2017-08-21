package com.nexusdevs.shoppersdeal.server.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.nexusdevs.shoppersdeal.server.db.MongoDBManager;
import com.nexusdevs.shoppersdeal.server.dto.User;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Service
@SuppressWarnings({ "unused"})
public class DAOService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static String USER_COLLECTION = "users";
	private static String USER_SESSION_COLLECTION = "user_session";

	@Autowired
	private MongoDBManager mongoDBManager;

	//get User From Document
	public User getUserFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		Object password = doc.remove("passcode");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		User userObject = new Gson().fromJson(dbObj.toString(), User.class);
		return userObject;
	}
	
	//get user session from document
		public UserSession getUserSessionFromDocument(Document doc) {
			Object idObj = doc.remove("_id");
			BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
			UserSession userSessionObject = new Gson().fromJson(dbObj.toString(), UserSession.class);
			return userSessionObject;
		}
	
	//create new user
	public String createUser(User user) {
		String userObj = new Gson().toJson(user);
		String addObject = mongoDBManager.addObject(USER_COLLECTION, userObj);
		return addObject;
	}
	
	//get user detail
	public User getUserDetail(String emailId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("email", emailId);
		Document document = mongoDBManager.getObject(USER_COLLECTION, queryParams);
		if (document == null)
			return null;
		User user = getUserFromDocument(document);
		return user;
	}
	
	//user login
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
	
	//create user session
	public void insertSession(UserSession session) {
		String sessionObj = new Gson().toJson(session);
		mongoDBManager.addObject(USER_SESSION_COLLECTION, sessionObj);
	}
	
	
	//logout user session
	public void logout(UserSession userSession) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("token", userSession.getToken());
		Map<String, Object> fieldValueMap = new HashMap<>();
		fieldValueMap.put("expired", true);
		mongoDBManager.setField(USER_SESSION_COLLECTION, queryParams, fieldValueMap, true, true);
	}
	
	//validate user session
	public UserSession validate(UserSession userSession) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("userId", userSession.getUserId());
		queryParams.put("token", userSession.getToken());
		Document document = mongoDBManager.getObject(USER_SESSION_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		return getUserSessionFromDocument(document);
	}
}