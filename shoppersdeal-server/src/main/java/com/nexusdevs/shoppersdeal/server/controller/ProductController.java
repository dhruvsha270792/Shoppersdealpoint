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
import com.nexusdevs.shoppersdeal.server.service.ProductDAOService;
import com.nexusdevs.shoppersdeal.server.service.ProductService;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;

@CrossOrigin
@Controller
@RequestMapping("/p")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/start")
	public String start() {
		return "abc";
	}
	
	/*@RequestMapping(value = "/menu/list")
	@ResponseBody
	public String getMenuCategory(@RequestParam(defaultValue = "10") int n, @RequestParam(defaultValue = "0") int pos) {
		try {
			String sortField = "createTime";
			String sortType = "DESCENDING";
			int total = 0;
			JsonArray menuList = productService.getMenuList(n, pos, sortField, sortType);
			return menuList.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("no data found").toString();
	}*/
	
	
	
}