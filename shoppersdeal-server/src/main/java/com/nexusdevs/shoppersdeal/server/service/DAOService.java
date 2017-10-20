package com.nexusdevs.shoppersdeal.server.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
	
	
	private <T> T getDocToClass(Document doc, Class<T> klass) {
		Object idObj = doc.remove("_id");
		BasicDBObject dbObj = (BasicDBObject) JSON.parse(doc.toJson());
		Gson gson = new Gson();
		T t = gson.fromJson(dbObj.toString(), klass);
		if (idObj != null) {
			String id = idObj.toString();
			try {
				Method[] methods = t.getClass().getMethods();
				for (Method m : methods) {
					if (m.getName().contains("setId")) {
						m.invoke(t, id);
					}
				}
			} catch (Exception e) {
			}
		}
		return t;
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
			int sort = 1;
			if (sT.equalsIgnoreCase("DESC")) {
				sort = -1;
			}
			sortingKey.put(sF, sort);
		}

		List<Document> objects = mongoDBManager.getObjects(CATEGORY_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Category> list = objects.stream().map(o -> getDocToClass(o, Category.class)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	//get category details
	public Category getCategoryDetails(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(categoryId));
		Document document = mongoDBManager.getObject(CATEGORY_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		Category category = getDocToClass(document, Category.class);
		return category;
	}
	
	//create new category
	public String createCategory(Category category) {
		String categoryObj = new Gson().toJson(category);
		String addObject = mongoDBManager.addObject(CATEGORY_COLLECTION, categoryObj);
		return addObject;
	}
	
	//update category
	public Category updateCategory(Category category) {
		JsonObject jsonObj = new Gson().toJsonTree(category).getAsJsonObject();
		jsonObj.remove("id");
		
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("_id", new ObjectId(category.getId()));
		
		Document doc = mongoDBManager.updateObject(CATEGORY_COLLECTION, queryParam, jsonObj.toString());
		if(doc == null)
			return null;
		
		return category;
	}
	
	//delete category
	public void deleteCategory(String categoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(categoryId));
		mongoDBManager.deleteObjects(CATEGORY_COLLECTION, queryParams);
	}
	
	
	//get subcategory list
	public List<SubCategory> getSubcategoryList(int n, int pos, String sF, String sT) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("deleted", false);
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null) {
			int sort = 1;
			if (sT.equalsIgnoreCase("DESC")) {
				sort = -1;
			}
			sortingKey.put(sF, sort);
		}

		List<Document> objects = mongoDBManager.getObjects(SUBCATEGORY_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<SubCategory> list = objects.stream().map(o -> getDocToClass(o, SubCategory.class)).collect(Collectors.toList());
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
	public SubCategory getSubcategoryDetails(String subcategoryId) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(subcategoryId));
		Document document = mongoDBManager.getObject(SUBCATEGORY_COLLECTION, queryParams);
		
		if (document == null)
			return null;
		
		SubCategory subcategory = getDocToClass(document, SubCategory.class);
		return subcategory;
	}
	
	//update subcategory
	public SubCategory updateSubcategory(SubCategory subcategory) {
		JsonObject jsonObj = new Gson().toJsonTree(subcategory).getAsJsonObject();
		jsonObj.remove("id");
		
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("_id", new ObjectId(subcategory.getId()));
		
		Document doc = mongoDBManager.updateObject(SUBCATEGORY_COLLECTION, queryParam, jsonObj.toString());
		if(doc == null)
			return null;
		
		return subcategory;
	}
	
	//delete subcategory
	public void deleteSubcategory(String subcategoryId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(subcategoryId));
		mongoDBManager.deleteObjects(SUBCATEGORY_COLLECTION, queryParams);
	}
	
	//get category list
	public List<Products> getProductList(int n, int pos, String sF, String sT) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("deleted", false);
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null) {
			int sort = 1;
			if (sT.equalsIgnoreCase("DESC")) {
				sort = -1;
			}
			sortingKey.put(sF, sort);
		}

		List<Document> objects = mongoDBManager.getObjects(PRODUCT_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Products> list = objects.stream().map(o -> getDocToClass(o, Products.class)).collect(Collectors.toList());
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
	public Products getProductDetails(String productId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(productId));
		
		Document document = mongoDBManager.getObject(PRODUCT_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		Products product = getDocToClass(document, Products.class);
		return product;
	}
	
	//update category
	public Products updateProduct(Products product) {
		
		JsonObject jsonObj = new Gson().toJsonTree(product).getAsJsonObject();
		jsonObj.remove("id");
		Map<String, Object> queryParam = new HashMap<>();
		queryParam.put("_id", new ObjectId(product.getId()));
		Document doc = mongoDBManager.updateObject(PRODUCT_COLLECTION, queryParam, jsonObj.toString());
		if(doc == null)
			return null;
		
		return product;
	}
	
	//temporary delete category
	public Boolean tempDeleteProduct(String productId) {
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(productId));
		
		Map<String, Object> fieldValue = new HashMap<>();
		fieldValue.put("deleted", true);
		
		Boolean status = mongoDBManager.setField(PRODUCT_COLLECTION, queryParams, fieldValue);
		return status;
	}
	
	//delete category
	public void deleteProduct(String productId) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("_id", new ObjectId(productId));
		mongoDBManager.deleteObjects(PRODUCT_COLLECTION, queryParams);
	}
	
	//Get Category By Name
	public Category getCategoryByName(String categoryName) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryName", categoryName);
		Document document = mongoDBManager.getObject(CATEGORY_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		Category category = getDocToClass(document, Category.class);
		return category;
	}
	
	//Get Subcategory By Name
	public SubCategory getSubcategoryByName(String categoryId, String subcategoryName) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("categoryId", categoryId);
		queryParams.put("subcategoryName", subcategoryName);
		Document document = mongoDBManager.getObject(SUBCATEGORY_COLLECTION, queryParams);
		if (document == null)
			return null;
		
		SubCategory subcategory = getDocToClass(document, SubCategory.class);
		return subcategory;
	}
	
	//get shop list	
	public List<Products> getShopList(int n, int pos, String categoryName, String subcategoryName, String productId, String sF, String sT) {
		BasicDBObject queryParams = new BasicDBObject();
		queryParams.put("deleted", false);
		
		Category category = getCategoryByName(categoryName);
		if(category != null) {
			List<String> categoryList = new ArrayList<String>();
			categoryList.add(category.getId());
			queryParams.put("category", new BasicDBObject("$in", categoryList));
			
			SubCategory subcategory = getSubcategoryByName(category.getId(), subcategoryName);
			if(subcategory != null) {
				List<String> subcategoryList = new ArrayList<String>();
				subcategoryList.add(subcategory.getId());
				queryParams.put("subcategory", new BasicDBObject("$in", subcategoryList));
			}
		}
		
		if(productId != null && !productId.equals(""))
			queryParams.put("productId", productId);
		
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null && !sF.equals("")) {
			int sort = 1;
			if (sT.equalsIgnoreCase("DESC")) {
				sort = -1;
			}
			sortingKey.put(sF, sort);
		}
		
		List<Document> objects = mongoDBManager.getObjectsInArray(PRODUCT_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Products> list = objects.stream().map(o -> getDocToClass(o, Products.class)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	
	//get hotdeals list
	public List<Products> getHotdealList(int n, int pos, String categoryName, String subcategoryName, String productId, String sF, String sT) {
		BasicDBObject queryParams = new BasicDBObject();
		queryParams.put("deleted", false);
		queryParams.put("dealCategory", "hotDeals");
		
		Category category = getCategoryByName(categoryName);
		if(category != null) {
			List<String> categoryList = new ArrayList<String>();
			categoryList.add(category.getId());
			queryParams.put("category", new BasicDBObject("$in", categoryList));
			
			SubCategory subcategory = getSubcategoryByName(category.getId(), subcategoryName);
			if(subcategory != null) {
				List<String> subcategoryList = new ArrayList<String>();
				subcategoryList.add(subcategory.getId());
				queryParams.put("subcategory", new BasicDBObject("$in", subcategoryList));
			}
		}
		
		if(productId != null && !productId.equals(""))
			queryParams.put("productId", productId);
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null) {
			int sort = -1;
			if (sT.equalsIgnoreCase("ASC")) {
				sort = 1;
			}
			sortingKey.put(sF, sort);
		}
		
		List<Document> objects = mongoDBManager.getObjectsInArray(PRODUCT_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Products> list = objects.stream().map(o -> getProductFromDocument(o)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
	}
	
	//get top rated list
	public List<Products> getTopRatedList(int n, int pos, String categoryName, String subcategoryName, String productId, String sF, String sT) {
		BasicDBObject queryParams = new BasicDBObject();
		queryParams.put("deleted", false);
		queryParams.put("dealCategory", "topRated");
		
		Category category = getCategoryByName(categoryName);
		if(category != null) {
			List<String> categoryList = new ArrayList<String>();
			categoryList.add(category.getId());
			queryParams.put("category", new BasicDBObject("$in", categoryList));
			
			SubCategory subcategory = getSubcategoryByName(category.getId(), subcategoryName);
			if(subcategory != null) {
				List<String> subcategoryList = new ArrayList<String>();
				subcategoryList.add(subcategory.getId());
				queryParams.put("subcategory", new BasicDBObject("$in", subcategoryList));
			}
		}
		
		Map<String, Object> sortingKey = new HashMap<>();
		if (sF != null) {
			int sort = -1;
			if (sT.equalsIgnoreCase("ASC")) {
				sort = 1;
			}
			sortingKey.put(sF, sort);
		}
		
		List<Document> objects = mongoDBManager.getObjectsInArray(PRODUCT_COLLECTION, pos, n, queryParams, sortingKey);
		if (objects != null) {
			List<Products> list = objects.stream().map(o -> getProductFromDocument(o)).collect(Collectors.toList());
			return list;
		}
		return Collections.emptyList();
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

/*Map<String, Object> queryParams = new HashMap<>();
queryParams.put("deleted", false);

Category category = getCategoryByName(categoryName);
if(category == null)
	return Collections.emptyList();
queryParams.put("category", category.getId());

if(subcategoryName != null && !subcategoryName.equals("")) {
	SubCategory subcategory = getSubcategoryByName(category.getId(), subcategoryName);
	if(subcategory != null && !subcategory.equals("")) {
		queryParams.put("subcategory", subcategory.getId());
	}
}

String sF = null;
String sT = null;
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
	List<Products> list = objects.stream().map(o -> getDocToClass(o, Products.class)).collect(Collectors.toList());
	return list;
}
return Collections.emptyList();

public List<Products> getShopList(int n, int pos, String categoryName, String subcategoryName) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("deleted", false);
		Category category = getCategoryByName(categoryName);
		if(category == null)
			return Collections.emptyList();
		
		List<String> categoryList = new ArrayList<String>();
		categoryList.add(category.getId());
		JsonArray categoryArr = new JsonArray();
		
		SubCategory subcategory = getSubcategoryByName(category.getId(), subcategoryName);
		List<String> subcategoryList = new ArrayList<String>();
		subcategoryList.add(subcategory.getId());
		JsonArray subcategoryArr = new JsonArray();
		
		if(subcategory == null)
			return Collections.emptyList();
		
		categoryList.stream().map(c -> c).forEach(j -> categoryArr.add(j));
		subcategoryList.stream().map(c -> c).forEach(j -> subcategoryArr.add(j));
		queryParams.put("category", categoryArr);
		queryParams.put("subcategory", subcategoryArr);
		
		
		String sF = null;
		String sT = null;
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
			List<Products> list = objects.stream().map(o -> getDocToClass(o, Products.class)).collect(Collectors.toList());
			return list;
		}
		System.out.println(objects);
		return Collections.emptyList();
	}
*/