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
import com.nexusdevs.shoppersdeal.server.dto.Products;
import com.nexusdevs.shoppersdeal.server.dto.SubCategory;
import com.nexusdevs.shoppersdeal.server.service.ConsoleService;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@Controller
@RequestMapping("/c")
public class ConsoleController {

	private static Logger logger = LoggerFactory.getLogger(ConsoleController.class);

	@Autowired
	private ConsoleService consoleService;
	
	/*Category Start*/
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
	public String getCategoryDetails(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to get category details");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
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
	
	
	@RequestMapping(value = "/archive/category", method = RequestMethod.POST)
	@ResponseBody
	public String archiveCategory(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to archive category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
			String status = consoleService.archiveCategory(category.getCategoryId());
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
	
	
	@RequestMapping(value = "/unarchive/category", method = RequestMethod.POST)
	@ResponseBody
	public String unarchiveCategory(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to unarchive category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
			String status = consoleService.unarchiveCategory(category.getCategoryId());
			if(status.equals("false"))
				return JsonUtils.errorResponse("unable to unarchive category").toString();
			
			jsonObject.addProperty("success", status);
			jsonObject.addProperty("data", "unarchive successfully");
			return jsonObject.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/delete/category", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCategory(@RequestBody String categoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to delete category");
		jsonObject.addProperty("success", false);
		try {
			Category category = new Gson().fromJson(categoryObjStr, Category.class);
			String status = consoleService.deleteCategory(category.getCategoryId());
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	/*Category End*/
	
	
	/*Sub-Category Start*/
	@RequestMapping(value = "/list/subcategory")
	@ResponseBody
	public String listSubcategory(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "ASCENDING";
			int total = 0;
			JsonArray subcategories = consoleService.subcategoryList(n, pos, sortField, sortType);
			if (subcategories == null)
				total = 0;
			else {
				total = subcategories.size();
			}
			return JsonUtils.createPaginatedResponse(subcategories, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}

	@RequestMapping(value = "/add/subcategory", method = RequestMethod.POST)
  	@ResponseBody
	public String addSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to add subcategory");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.addSubcategory(subcategory);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/get/subcategory", method = RequestMethod.POST)
  	@ResponseBody
	public String getSubcategoryDetails(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to get subcategory details");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subCategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.getSubcategoryDetails(subCategory);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/update/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String updateSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to update subcategory");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.updateSubcategory(subcategory);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/archive/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String archiveSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to archive subcategory");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.archiveSubcategory(subcategory);
			if(status.equals("false"))
				return JsonUtils.errorResponse("unable to archive subcategory").toString();
			
			jsonObject.addProperty("success", status);
			jsonObject.addProperty("data", "archive successfully");
			return jsonObject.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/unarchive/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String unarchiveSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to unarchive subcategory");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.unarchiveSubcategory(subcategory);
			if(status.equals("false"))
				return JsonUtils.errorResponse("unable to unarchive subcategory").toString();
			
			jsonObject.addProperty("success", status);
			jsonObject.addProperty("data", "unarchive successfully");
			return jsonObject.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}

	@RequestMapping(value = "/delete/subcategory", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSubcategory(@RequestBody String subcategoryObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to delete subcategory");
		jsonObject.addProperty("success", false);
		try {
			SubCategory subcategory = new Gson().fromJson(subcategoryObjStr, SubCategory.class);
			String status = consoleService.deleteSubcategory(subcategory);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	/*Sub-Category End*/
	
	
	/*Product Start*/
	@RequestMapping(value = "/list/product")
	@ResponseBody
	public String listProduct(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "ASCENDING";
			int total = 0;
			JsonArray products = consoleService.productList(n, pos, sortField, sortType);
			if (products == null)
				total = 0;
			else {
				total = products.size();
			}
			return JsonUtils.createPaginatedResponse(products, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}
	
	@RequestMapping(value = "/add/product", method = RequestMethod.POST)
  	@ResponseBody
	public String addProduct(@RequestBody String productObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to add product");
		jsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			String status = consoleService.addProduct(product);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/get/product", method = RequestMethod.POST)
  	@ResponseBody
	public String getProductDetails(@RequestBody String productObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to get product details");
		jsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			String status = consoleService.getProductDetails(product);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/update/product", method = RequestMethod.POST)
	@ResponseBody
	public String updateProduct(@RequestBody String productObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to update product");
		jsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			String status = consoleService.updateProduct(product);
			return status;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/temp/delete/product", method = RequestMethod.POST)
	@ResponseBody
	public String tempDeleteProduct(@RequestBody String productObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to archive product");
		jsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			String status = consoleService.tempDeleteProduct(product);
			if(status.equals("false"))
				return JsonUtils.errorResponse("unable to archive product").toString();
			
			jsonObject.addProperty("success", status);
			jsonObject.addProperty("data", "archive successfully");
			return jsonObject.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/delete/product", method = RequestMethod.POST)
	@ResponseBody
	public String deleteProduct(@RequestBody String productObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Error to delete product");
		jsonObject.addProperty("success", false);
		try {
			Products product = new Gson().fromJson(productObjStr, Products.class);
			String status = consoleService.deleteProduct(product);
			return status.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	/*Product End*/
}