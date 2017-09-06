package com.nexusdevs.shoppersdeal.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.Category;
import com.nexusdevs.shoppersdeal.server.service.ConsoleService;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Controller
@RequestMapping("/c")
public class ConsoleController {

	private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

	@Autowired
	private ConsoleService consoleService;

	@RequestMapping(value = "/list/category")
	@ResponseBody
	public String listCategory(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "ASCENDING";
			int total = 0;
			JsonArray categories = consoleService.categoryList(n, pos, sortField, sortType);
			if (categories == null)
				total = 0;
			else {
				total = categories.size();
			}
			return JsonUtils.createPaginatedResponse(categories, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}

	@RequestMapping(value = "/add/category", method = RequestMethod.POST)
  	@ResponseBody
	public String addCategory(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to add category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
			String status = consoleService.addCategory(category);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/get/category", method = RequestMethod.POST)
  	@ResponseBody
	public String getCategoryDetails(@RequestBody String categoryIdObj) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to get category details");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryIdObj, Category.class);
			String status = consoleService.getCategoryDetails(category.getCategoryId());
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/update/category", method = RequestMethod.POST)
	@ResponseBody
	public String updateCategory(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to update category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
			String status = consoleService.updateCategory(category);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/temp/delete/category", method = RequestMethod.POST)
	@ResponseBody
	public String tempDeleteCategory(@RequestBody String categoryIdObj) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to archive category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryIdObj, Category.class);
			String status = consoleService.tempDeleteCategory(category.getCategoryId());
			if(status.equals("false"))
				return JsonUtils.errorResponse("unable to archive category").toString();
			
			jsonObject.addProperty("success", status);
			jsonObject.addProperty("data", "archive successfully");
			return jsonObject.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/delete/category", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCategory(@RequestBody String categoryId) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to delete category");
		JsonObject.addProperty("success", false);
		try {
			String status = consoleService.deleteCategory(categoryId);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	/*@RequestMapping(value = "/list/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String listSubcategory(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "ASCENDING";
			int total = 0;
			List<SubCategory> subcategories = consoleService.subCategoryList(n, pos, sortField, sortType);
			if (subcategories == null)
				total = 0;

			JsonArray subcategoryArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			subcategories.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, SubCategory.class))).forEach(j -> subcategoryArray.add(j));
			total = subcategoryArray.size();
			return JsonUtils.createPaginatedResponse(subcategoryArray, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}

	@RequestMapping(value = "/add/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String addSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to add subcategory");
		JsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			JsonObject status = consoleService.addSubCategory(subcategory);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/update/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String updateSubCategory(@RequestBody String subcategoryObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to update subcategory");
		JsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			JsonObject status = consoleService.updateSubCategory(subcategory);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/temp/delete/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String tempDeleteSubCategory(@RequestBody String subcategoryIdObj) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to archive subcategory");
		JsonObject.addProperty("success", false);
		try {
			JsonObject status = consoleService.tempDeleteSubCategory(subcategoryIdObj);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/delete/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSubCategory(@RequestBody String subcategoryIdObj) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to delete subcategory");
		JsonObject.addProperty("success", false);
		try {
			JsonObject status = consoleService.deleteSubCategory(subcategoryIdObj);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}
	
	@RequestMapping(value = "/list/product", method = RequestMethod.POST)
	@ResponseBody
	public String listProduct(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "ASCENDING";
			int total = 0;
			List<Products> products = consoleService.productList(n, pos, sortField, sortType);
			if (products == null)
				total = 0;

			JsonArray productArray = new JsonArray();
			JsonParser jsParser = new JsonParser();
			products.stream().map(o -> (JsonObject) jsParser.parse(new Gson().toJson(o, Products.class))).forEach(j -> productArray.add(j));
			total = productArray.size();
			return JsonUtils.createPaginatedResponse(productArray, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}

	@RequestMapping(value = "/add/product", method = RequestMethod.POST)
	@ResponseBody
	public String addProduct(@RequestBody String productObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to add product");
		JsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			JsonObject status = consoleService.addProduct(product);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/update/product", method = RequestMethod.POST)
	@ResponseBody
	public String updateProduct(@RequestBody String productObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to update product");
		JsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			JsonObject status = consoleService.updateProduct(product);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/temp/delete/product", method = RequestMethod.POST)
	@ResponseBody
	public String tempDeleteProduct(@RequestBody String productIdObj) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to archive product");
		JsonObject.addProperty("success", false);
		try {
			JsonObject status = consoleService.tempDeleteProduct(productIdObj);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}

	@RequestMapping(value = "/delete/product", method = RequestMethod.POST)
	@ResponseBody
	public String deleteProduct(@RequestBody String productIdObj) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("data", "Error to delete product");
		JsonObject.addProperty("success", false);
		try {
			JsonObject status = consoleService.deleteProduct(productIdObj);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}*/	
}