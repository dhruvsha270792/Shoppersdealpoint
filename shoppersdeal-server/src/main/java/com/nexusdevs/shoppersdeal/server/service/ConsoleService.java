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
import com.nexusdevs.shoppersdeal.server.utils.StringUtils;

@Service
public class ConsoleService {
	
	private static Logger logger= LoggerFactory.getLogger(ConsoleService.class);
	
	@Autowired
	private DAOService daoService;
	
	/*Category Start*/
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
			category.setCreateTime(System.currentTimeMillis());
			category.setUpdateTime(0);
			category.setDeleted(false);
			
			String categoryStatus = daoService.createCategory(category);
			if(categoryStatus == null || categoryStatus.equals(""))
				return JsonUtils.errorResponse("error to create category").toString();
			
			return JsonUtils.successResponse(categoryStatus).toString();
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to create category").toString();
	}
	
	
	public String getCategoryDetails(String categoryIdStr){
		try{
			Category category = daoService.getCategoryDetails(categoryIdStr);
			if(category == null)
				return JsonUtils.errorResponse("no category found").toString();
			
			return new Gson().toJson(category);
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get category details").toString();
	}
	
	
	public String updateCategory(Category category) {
		try {
			String oldCategory = getCategoryDetails(category.getId());
			Category oldCategoryObj = new Gson().fromJson(oldCategory, Category.class);
			
			oldCategoryObj.setCategoryName(category.getCategoryName());
			oldCategoryObj.setUpdateTime(System.currentTimeMillis());
			
			Category categoryStatus = daoService.updateCategory(oldCategoryObj);
			
			if(categoryStatus == null)
				return JsonUtils.errorResponse("error to update category").toString();
			
			return new Gson().toJson(oldCategoryObj);
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to update category").toString();
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
		return JsonUtils.errorResponse("error in get category details").toString();
	}
	/*Category End*/
	
	
	/*Subcategory Start*/
	public JsonArray subcategoryList(int n, int pos, String sF, String sT) {
		try {
			List<SubCategory> subcategoryList = daoService.getSubcategoryList(n, pos, sF, sT);
			JsonArray subcategoryArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			subcategoryList.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, SubCategory.class))).forEach(j -> subcategoryArray.add(j));
			return subcategoryArray;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public String addSubcategory(SubCategory subcategory) {
		try {
			subcategory.setCreateTime(System.currentTimeMillis());
			subcategory.setUpdateTime(0);
			subcategory.setDeleted(false);
			
			String subcategoryStatus = daoService.createSubcategory(subcategory);
			if(subcategoryStatus == null || subcategoryStatus.equals(""))
				return JsonUtils.errorResponse("error to create subcategory").toString();
			
			return JsonUtils.successResponse(subcategoryStatus).toString();
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to create subcategory").toString();
	}
	
	
	public String getSubcategoryDetails(String subCategoryObj) {
		try{
			SubCategory subcategory = daoService.getSubcategoryDetails(subCategoryObj);
			if(subcategory == null)
				return JsonUtils.errorResponse("no subcategory found").toString();
			
			String subcategoryDetails = new Gson().toJson(subcategory);
			return subcategoryDetails;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get subcategory details").toString();
	}
	
	public String updateSubcategory(SubCategory subcategory) {
		try {
			String oldSubcategory = getSubcategoryDetails(subcategory.getId());
			SubCategory oldSubcategoryObj = new Gson().fromJson(oldSubcategory, SubCategory.class);
			
			oldSubcategoryObj.setCategoryId(subcategory.getCategoryId());
			oldSubcategoryObj.setSubcategoryName(subcategory.getSubcategoryName());
			oldSubcategoryObj.setUpdateTime(System.currentTimeMillis());
			
			SubCategory subcategoryStatus = daoService.updateSubcategory(oldSubcategoryObj);
			if(subcategoryStatus == null)
				return JsonUtils.errorResponse("error to update category").toString();
			
			return new Gson().toJson(oldSubcategoryObj);
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to update subcategory").toString();
	}
	
	public String deleteSubcategory(String subCategoryObjId) {
		try{
			String subcategory = getSubcategoryDetails(subCategoryObjId);
			if(subcategory == null || subcategory.equals(""))
				return JsonUtils.errorResponse("no subcategory found").toString();
			
			daoService.deleteSubcategory(subCategoryObjId);
			return JsonUtils.successResponse("delete successfully").toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get details").toString();
	}
	/*Subcategory End*/
	
	
	/*Product Start*/
	public JsonArray productList(int n, int pos, String sF, String sT) {
		try {
			List<Products> productList = daoService.getProductList(n, pos, sF, sT);
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
	
	
	public String addProduct(Products product) {
		try {
			product.setProductId(StringUtils.getSaltString());
			product.setCreateTime(System.currentTimeMillis());
			product.setUpdateTime(0);
			product.setDeleted(false);
			
			String productStatus = daoService.createProduct(product);
			if(productStatus == null || productStatus.equals(""))
				return JsonUtils.errorResponse("error to create subcategory").toString();
			
			return JsonUtils.successResponse(productStatus).toString();
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to create subcategory").toString();
	}
	
	
	public String getProductDetails(String productObjId) {
		try{
			Products product = daoService.getProductDetails(productObjId);
			if(product == null)
				return JsonUtils.errorResponse("no product found").toString();
			
			String productDetails = new Gson().toJson(product);
			return productDetails;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get subcategory details").toString();
	}
	
	
	public String updateProduct(Products product) {
		try {
			String oldProduct = getProductDetails(product.getId());
			Products oldProductsObj = new Gson().fromJson(oldProduct, Products.class);
			
			oldProductsObj.setProductName(product.getProductName());
			oldProductsObj.setCompName(product.getCompName());
			oldProductsObj.setPrice(product.getPrice());
			oldProductsObj.setDiscPrice(product.getDiscPrice());
			oldProductsObj.setSummary(product.getSummary());
			
			if(product.getImages() != null)
				oldProductsObj.setImages(product.getImages());
			
			if(product.getCategory() != null)
				oldProductsObj.setCategory(product.getCategory());
			
			if(product.getSubcategory() != null)
				oldProductsObj.setSubcategory(product.getSubcategory());
			
			if(product.getTags() != null)
				oldProductsObj.setTags(product.getTags());
			
			oldProductsObj.setDealCategory(product.getDealCategory());
			oldProductsObj.setUpdateTime(System.currentTimeMillis());
			
			Products productStatus = daoService.updateProduct(oldProductsObj);
			if(productStatus == null)
				return JsonUtils.errorResponse("error to update category").toString();
			
			return JsonUtils.successResponse(productStatus.getId()).toString();
		}
		catch(Exception e) {}
		return JsonUtils.errorResponse("error to update subcategory").toString();
	}
	
	public String tempDeleteProduct(String productId){
		try{
			String productObj = getProductDetails(productId);
			if(productObj == null || productObj.equals(""))
				return JsonUtils.errorResponse("no product found").toString();
			
			Boolean temDeleteStatus = daoService.tempDeleteProduct(productId);
			return temDeleteStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get subcategory details").toString();
	}
	
	
	public String deleteProduct(String productId) {
		try{
			String productObj = getProductDetails(productId);
			if(productObj == null || productObj.equals(""))
				return JsonUtils.errorResponse("no product found").toString();
			
			daoService.deleteProduct(productId);
			return JsonUtils.successResponse("delete successfully").toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return JsonUtils.errorResponse("error in get details").toString();
	}
	/*Product End*/
}