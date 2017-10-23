package com.nexusdevs.shoppersdeal.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.ConsoleUser;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.service.AdminService;

public class AdminController {
	
	private static Logger logger= LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private AdminService adminService;
	
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public String registerConsoleUser(@RequestBody String userObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("success", false);
		JsonObject.addProperty("data", "Error to create user");
		try {
			ConsoleUser userObj = new Gson().fromJson(userObjStr, ConsoleUser.class);
			JsonObject registerStatus = adminService.registerConsoleUser(userObj);
			return registerStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginConsoleUser(@RequestBody String loginObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Invalid Username/Password");
		jsonObject.addProperty("success", false);
		try {
			ConsoleUser user = new Gson().fromJson(loginObjStr, ConsoleUser.class);
			JsonObject loginStatus = adminService.loginConsoleUser(user);
			return loginStatus.toString();
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String logoutConsoleUser(@RequestBody String logoutObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "logout unsuccessful");
		jsonObject.addProperty("success", false);
		try {
			UserSession userSession = new Gson().fromJson(logoutObjStr, UserSession.class);
			JsonObject logoutStatus = adminService.logoutConsoleUser(userSession);
			jsonObject.add("data", logoutStatus);
			jsonObject.addProperty("success", true);
			return jsonObject.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public String validateConsoleUser(@RequestBody String validateTokenStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "invalid token");
		jsonObject.addProperty("success", false);
		try{
			UserSession userSession = new Gson().fromJson(validateTokenStr, UserSession.class);
			JsonObject validateStatus = adminService.validateConsoleUser(userSession);
			return validateStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}
}
