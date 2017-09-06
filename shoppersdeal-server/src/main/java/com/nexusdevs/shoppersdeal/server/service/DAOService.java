package com.nexusdevs.shoppersdeal.server.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.nexusdevs.shoppersdeal.server.dto.Category;
import com.nexusdevs.shoppersdeal.server.dto.User;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Service
@SuppressWarnings({ "unused"})
public class DAOService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static String USER_COLLECTION = "users";
	private static String USER_SESSION_COLLECTION = "user_session";
	private static String CATEGORY_COLLECTION = "categories";

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
	
	//get category from document
	public Category getCategoryFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		Category categoryObject = new Gson().fromJson(dbObj.toString(), Category.class);
		return categoryObject;
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
	
	//get category list
	public List<Category> getCategoryList(int n, int pos, String sF, String sT) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("deleted", false);
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null) {
			int sort = -1;
			if (sT.equalsIgnoreCase("ASC")) {
				sort = 1;
			}
			sortingKey.put(sF, sort);
		}

		List<Document> objects = mongoDBManager.getObjects(CATEGORY_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Category> list = objects.stream().map(o -> getCategoryFromDocument(o)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	//create new category
	public String createCategory(Category category) {
		String categoryObj = new Gson().toJson(category);
		String addObject = mongoDBManager.addObject(CATEGORY_COLLECTION, categoryObj);
		return addObject;
	}
	
	//get category details
	public Category getCategoryDetails(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		Document document = mongoDBManager.getObject(CATEGORY_COLLECTION, queryParams);
		if (document == null)
			return null;
		Category category = getCategoryFromDocument(document);
		return category;
	}
	
	//update category
	public Category updateCategory(Category category) {
		String json = new Gson().toJson(category);
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("categoryId", category.getCategoryId());
		Document doc = mongoDBManager.updateObject(CATEGORY_COLLECTION, queryParam, json);
		if(doc == null)
			return null;
		return getCategoryFromDocument(doc);
	}
	
	//temporary delete category
	public Boolean tempDeleteCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", true);
		Boolean status = mongoDBManager.setField(CATEGORY_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	//delete category
	public void deleteCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		mongoDBManager.deleteObjects(CATEGORY_COLLECTION, queryParams);
	}
}