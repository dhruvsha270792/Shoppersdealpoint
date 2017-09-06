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
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Service
public class ConsoleService {
	
	private static Logger logger= LoggerFactory.getLogger(ConsoleService.class);
	
	@Autowired
	private DAOService daoService;
	
	public JsonArray categoryList(int n, int pos, String sF, String sT) {
		try {
			List<Category> categoryList = daoService.getCategoryList(n, pos, sF, sT);
			JsonArray categoryArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			categoryList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Category.class))).forEach(j -> categoryArray.add(j));
			return categoryArray;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public String addCategory(Category category) {
		try {
			category.setCategoryId("cat_"+System.currentTimeMillis());
			category.setCreateTime(System.currentTimeMillis());
			category.setUpdateTime(0);
			category.setDeleted(false);
			
			String categoryStatus = daoService.createCategory(category);
			if(categoryStatus == null || categoryStatus.equals(""))
				return JsonUtils.errorResponse("error to create category").toString();
			
			return new Gson().toJson(category).toString();
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to create category").toString();
	}
	
	
	public String getCategoryDetails(String categoryIdStr){
		try{
			Category category = daoService.getCategoryDetails(categoryIdStr);
			if(category == null || category.equals(""))
				return JsonUtils.errorResponse("no category found").toString();
			
			return new Gson().toJson(category);
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get details").toString();
	}
	
	
	public String updateCategory(Category category) {
		try {
			String oldCategory = getCategoryDetails(category.getCategoryId());
			Category oldCategoryObj = new Gson().fromJson(oldCategory, Category.class);
			
			oldCategoryObj.setCategory(category.getCategory());
			oldCategoryObj.setUpdateTime(System.currentTimeMillis());
			
			Category categoryStatus = daoService.updateCategory(oldCategoryObj);
			if(categoryStatus == null || categoryStatus.equals(""))
				return JsonUtils.errorResponse("error to update category").toString();
			
			return new Gson().toJson(oldCategoryObj);
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to update category").toString();
	}
	
	
	public String tempDeleteCategory(String categoryIdStr){
		try{
			String category = getCategoryDetails(categoryIdStr);
			if(category == null || category.equals(""))
				return JsonUtils.errorResponse("no category found").toString();
			
			Boolean temDeleteStatus = daoService.tempDeleteCategory(categoryIdStr);
			return temDeleteStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get details").toString();
	}
	
	
	public String deleteCategory(String categoryIdStr){
		try{
			String category = getCategoryDetails(categoryIdStr);
			if(category == null || category.equals(""))
				return JsonUtils.errorResponse("no category found").toString();
			
			daoService.deleteCategory(categoryIdStr);
			return JsonUtils.successResponse("delete successfully").toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get details").toString();
	}
}