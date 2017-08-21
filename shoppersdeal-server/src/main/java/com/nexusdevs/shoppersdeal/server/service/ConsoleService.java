package com.nexusdevs.shoppersdeal.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.Category;

@Service
public class ConsoleService {
	
	private static Logger logger= LoggerFactory.getLogger(ConsoleService.class);
	
	@Autowired
	private DAOService daoService;
	
	/*public List<Category> categoryList(int n, int pos, String sF, String sT) {
		try {
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public JsonObject addCategory(Category category) {
		try {
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}*/
	
}
