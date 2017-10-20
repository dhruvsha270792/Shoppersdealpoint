package com.nexusdevs.shoppersdeal.server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nexusdevs.shoppersdeal.server.dto.Category;
import com.nexusdevs.shoppersdeal.server.dto.Products;
import com.nexusdevs.shoppersdeal.server.dto.SubCategory;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Service
public class ProductService {
	
private static Logger logger= LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private DAOService daoService;
	
	public String getMenuList() {
		try {
			JsonParser jsParser = new JsonParser();
			
			List<Category> categoryList = daoService.getCategoryList(0, 0, null, null);
			JsonArray categoryArray = new JsonArray();
			categoryList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Category.class))).forEach(j -> categoryArray.add(j));
			
			List<SubCategory> subcategoryList = daoService.getSubcategoryList(0, 0, null, null);
			JsonArray subcategoryArray = new JsonArray();
			subcategoryList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, SubCategory.class))).forEach(j -> subcategoryArray.add(j));
			
			List<JsonObject> menuList = JsonUtils.mergeById(categoryArray, subcategoryArray);
			return menuList.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public JsonArray getShopList(int n, int pos, String categoryName, String subcategoryName, String productId, String sortField, String sortType) {
		try {
			List<Products> productList = daoService.getShopList(n, pos, categoryName, subcategoryName, productId, sortField, sortType);
			JsonArray productArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			productList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Products.class))).forEach(j -> productArray.add(j));
			return productArray;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public JsonArray getHotdealList(int n, int pos, String categoryName, String subcategoryName, String productId, String sortField, String sortType) {
		try {
			List<Products> hotdealList = daoService.getHotdealList(n, pos, categoryName, subcategoryName, productId, sortField, sortType);
			JsonArray hotdealArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			hotdealList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Products.class))).forEach(j -> hotdealArray.add(j));
			return hotdealArray;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public JsonArray getTopRatedList(int n, int pos, String categoryName, String subcategoryName, String productId, String sortField, String sortType) {
		try {
			List<Products> topRatedList = daoService.getTopRatedList(n, pos, categoryName, subcategoryName, productId, sortField, sortType);
			JsonArray topRatedArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			topRatedList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Products.class))).forEach(j -> topRatedArray.add(j));
			return topRatedArray;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}

}
