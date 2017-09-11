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
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import com.nexusdevs.shoppersdeal.server.db.MongoDBManager;
import com.nexusdevs.shoppersdeal.server.dto.Category;
import com.nexusdevs.shoppersdeal.server.dto.ConsoleUser;
import com.nexusdevs.shoppersdeal.server.dto.Products;
import com.nexusdevs.shoppersdeal.server.dto.SubCategory;
import com.nexusdevs.shoppersdeal.server.dto.User;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;

@Service
@SuppressWarnings({ "unused"})
public class DAOService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private static String USER_COLLECTION = "user";
	private static String USER_SESSION_COLLECTION = "user_session";
	private static String CATEGORY_COLLECTION = "category";
	private static String SUBCATEGORY_COLLECTION = "subcategory";
	private static String PRODUCT_COLLECTION = "product";
	private static String CONSOLE_USER_COLLECTION = "console_user";
	private static String CONSOLE_USER_SESSION_COLLECTION = "console_user_session";

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
	
	//get subcategory from document
	public SubCategory getSubcategoryFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		SubCategory subcategoryObject = new Gson().fromJson(dbObj.toString(), SubCategory.class);
		return subcategoryObject;
	}
	
	//get product from document
	public Products getProductFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		Products productObject = new Gson().fromJson(dbObj.toString(), Products.class);
		return productObject;
	}
	
	//get product from document
	public ConsoleUser getConsoleUserFromDocument(Document doc) {
		Object idObj = doc.remove("_id");
		Object password = doc.remove("hp");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		ConsoleUser consoleUserObject = new Gson().fromJson(dbObj.toString(), ConsoleUser.class);
		return consoleUserObject;
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
	
	//archive category
	public Boolean archiveCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", true);
		Boolean status = mongoDBManager.setField(CATEGORY_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	
	//archive category
	public Boolean unarchiveCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", false);
		Boolean status = mongoDBManager.setField(CATEGORY_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	
	//delete category
	public void deleteCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		mongoDBManager.deleteObjects(CATEGORY_COLLECTION, queryParams);
	}
	
	
	//get category list
	public List<SubCategory> getSubcategoryList(int n, int pos, String sF, String sT) {
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

		List<Document> objects = mongoDBManager.getObjects(SUBCATEGORY_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<SubCategory> list = objects.stream().map(o -> getSubcategoryFromDocument(o)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	//create new subcategory
	public String createSubcategory(SubCategory subcategory) {
		String subcategoryObj = new Gson().toJson(subcategory);
		String addObject = mongoDBManager.addObject(SUBCATEGORY_COLLECTION, subcategoryObj);
		return addObject;
	}
	
	//get subcategory details
	public SubCategory getSubcategoryDetails(SubCategory subcategory) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", subcategory.getCategoryId());
		queryParams.put("subCategoryId", subcategory.getSubCategoryId());
		
		Document document = mongoDBManager.getObject(SUBCATEGORY_COLLECTION, queryParams);
		
		if (document == null)
			return null;
		
		SubCategory subcategoryDoc = getSubcategoryFromDocument(document);
		return subcategoryDoc;
	}
	
	//update subcategory
	public SubCategory updateSubcategory(SubCategory subcategory) {
		String json = new Gson().toJson(subcategory);
		
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("subCategoryId", subcategory.getSubCategoryId());
		
		Document doc = mongoDBManager.updateObject(SUBCATEGORY_COLLECTION, queryParam, json);
		
		if(doc == null)
			return null;
		
		return getSubcategoryFromDocument(doc);
	}
	
	//archive subcategory
	public Boolean archiveSubcategory(SubCategory subcategory) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", subcategory.getCategoryId());
		queryParams.put("subCategoryId", subcategory.getSubCategoryId());
		
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", true);
		
		Boolean status = mongoDBManager.setField(SUBCATEGORY_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	//unarchive subcategory
	public Boolean unarchiveSubcategory(SubCategory subcategory) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", subcategory.getCategoryId());
		queryParams.put("subCategoryId", subcategory.getSubCategoryId());
		
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", false);
		
		Boolean status = mongoDBManager.setField(SUBCATEGORY_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	//delete subcategory
	public void deleteSubcategory(SubCategory subcategory) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", subcategory.getCategoryId());
		queryParams.put("subCategoryId", subcategory.getSubCategoryId());
		mongoDBManager.deleteObjects(SUBCATEGORY_COLLECTION, queryParams);
	}
	
	//get category list
	public List<Products> getProductList(int n, int pos, String sF, String sT) {
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

		List<Document> objects = mongoDBManager.getObjects(PRODUCT_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Products> list = objects.stream().map(o -> getProductFromDocument(o)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	//create new product
	public String createProduct(Products product) {
		String productObj = new Gson().toJson(product);
		String addObject = mongoDBManager.addObject(PRODUCT_COLLECTION, productObj);
		return addObject;
	}
	
	//get product details
	public Products getProductDetails(Products product) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("productId", product.getProductId());
		Document document = mongoDBManager.getObject(PRODUCT_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		Products productDoc = getProductFromDocument(document);
		return productDoc;
	}
	
	//update category
	public Products updateProduct(Products product) {
		String json = new Gson().toJson(product);
		
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("productId", product.getProductId());
		Document doc = mongoDBManager.updateObject(PRODUCT_COLLECTION, queryParam, json);
		if(doc == null)
			return null;
		
		return getProductFromDocument(doc);
	}
	
	//temporary delete category
	public Boolean tempDeleteProduct(Products product) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("productId", product.getProductId());
		
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", true);
		
		Boolean status = mongoDBManager.setField(PRODUCT_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	//delete category
	public void deleteSubcategory(Products product) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("productId", product.getProductId());
		mongoDBManager.deleteObjects(PRODUCT_COLLECTION, queryParams);
	}
	
	//create new console user
	public String createConsoleUser(ConsoleUser user) {
		String userObj = new Gson().toJson(user);
		String addObject = mongoDBManager.addObject(CONSOLE_USER_COLLECTION, userObj);
		return addObject;
	}
	
	//get console user detail
	public ConsoleUser getConsoleUserDetail(String emailId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("email", emailId);
		Document document = mongoDBManager.getObject(CONSOLE_USER_COLLECTION, queryParams);
		if (document == null)
			return null;
		ConsoleUser user = getConsoleUserFromDocument(document);
		return user;
	}
	
	//console user login
	public ConsoleUser loginConsoleUser(String email, String pass) {
		try {
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("email", email);
			queryParams.put("passcode", pass);
			Document document = mongoDBManager.getObject(CONSOLE_USER_COLLECTION, queryParams);
			if (document != null)
				return getConsoleUserFromDocument(document);
		}
		catch (Exception e) {}
		return null;
	}
	
	//create console user session
	public void insertConsoleSession(UserSession session) {
		String sessionObj = new Gson().toJson(session);
		mongoDBManager.addObject(CONSOLE_USER_SESSION_COLLECTION, sessionObj);
	}
	
	//logout console user session
	public void logoutConsoleUser(UserSession userSession) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("token", userSession.getToken());
		Map<String, Object> fieldValueMap = new HashMap<>();
		fieldValueMap.put("expired", true);
		mongoDBManager.setField(CONSOLE_USER_SESSION_COLLECTION, queryParams, fieldValueMap, true, true);
	}
	
	//validate console user session
	public UserSession validateConsoleUser(UserSession userSession) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("userId", userSession.getUserId());
		queryParams.put("token", userSession.getToken());
		Document document = mongoDBManager.getObject(CONSOLE_USER_SESSION_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		return getUserSessionFromDocument(document);
	}
	
}