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
import com.nexusdevs.shoppersdeal.server.dto.Products;

@Service
public class ProductService {
	
private static Logger logger= LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private DAOService daoService;
	
	public JsonArray getShopList(int n, int pos, String categoryId, String subcategoryId) {
		try {
			List<Products> productList = daoService.getShopList(n, pos, categoryId, subcategoryId);
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
	
	public JsonArray getHotdealList(int n, int pos, String categoryId, String subcategoryId) {
		try {
			List<Products> hotdealList = daoService.getHotdealList(n, pos, categoryId, subcategoryId);
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
	
	public JsonArray getTopRatedList(int n, int pos, String categoryId, String subcategoryId) {
		try {
			List<Products> topRatedList = daoService.getTopRatedList(n, pos, categoryId, subcategoryId);
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
