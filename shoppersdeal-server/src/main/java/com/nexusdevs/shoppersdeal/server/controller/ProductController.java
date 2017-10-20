package com.nexusdevs.shoppersdeal.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.nexusdevs.shoppersdeal.server.service.ProductService;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@CrossOrigin
@Controller
@RequestMapping("/p/")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "menu/list")
	@ResponseBody
	public String getMenuCategory() {
		try {
			String menuList = productService.getMenuList();
			return menuList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}
	
	@RequestMapping(value = "shop")
	@ResponseBody
	public String getShopList(
			@RequestParam(defaultValue = "10") int n,
			@RequestParam(defaultValue = "0") int pos,
			@RequestParam(required=false) String categoryName,
			@RequestParam(required=false) String subcategoryName,
			@RequestParam(required=false) String apexId,
			@RequestParam(defaultValue = "createTime") String sortField,
			@RequestParam(defaultValue = "DESC") String sortType
			) {
		try {
			int total = 10;
			JsonArray shopList = productService.getShopList(n, pos, categoryName, subcategoryName, apexId, sortField, sortType);
			return JsonUtils.createPaginatedResponse(shopList, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}
	
	@RequestMapping(value = "hotdeals")
	@ResponseBody
	public String getHotdealList(
			@RequestParam(defaultValue = "10") int n,
			@RequestParam(defaultValue = "0") int pos,
			@RequestParam(required=false) String categoryName,
			@RequestParam(required=false) String subcategoryName,
			@RequestParam(required=false) String apexId,
			@RequestParam(defaultValue = "createTime") String sortField,
			@RequestParam(defaultValue = "DESC") String sortType
			) {
		try {
			int total = 10;
			JsonArray shopList = productService.getHotdealList(n, pos, categoryName, subcategoryName, apexId, sortField, sortType);
			return JsonUtils.createPaginatedResponse(shopList, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}
	
	@RequestMapping(value = "topRated")
	@ResponseBody
	public String getTopRatedList(
			@RequestParam(defaultValue = "10") int n,
			@RequestParam(defaultValue = "0") int pos,
			@RequestParam(required=false) String categoryName,
			@RequestParam(required=false) String subcategoryName,
			@RequestParam(required=false) String apexId,
			@RequestParam(defaultValue = "createTime") String sortField,
			@RequestParam(defaultValue = "DESC") String sortType
			) {
		try {
			int total = 10;
			JsonArray topRatedList = productService.getTopRatedList(n, pos, categoryName, subcategoryName, apexId, sortField, sortType);
			return JsonUtils.createPaginatedResponse(topRatedList, total, pos).toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}
}