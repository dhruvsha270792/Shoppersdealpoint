package com.nexusdevs.shoppersdeal.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexusdevs.shoppersdeal.server.service.ProductDAOService;
import com.nexusdevs.shoppersdeal.server.service.ProductService;

@Controller
@RequestMapping("/p")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductDAOService productDaoService;
	
	@RequestMapping("/start")
	public String startHere() {
		return "Aaja beta";
	}
}