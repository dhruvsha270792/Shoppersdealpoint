package com.nexusdevs.shoppersdeal.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.User;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.service.UserService;

@RestController
@RequestMapping("/u")
@SuppressWarnings({"unchecked"})
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/start")
	public String startHere() {
		return "Chal gaya";
	}
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public String registerUser(@RequestBody String userObjStr) {
		JsonObject JsonObject = new JsonObject();
		JsonObject.addProperty("success", false);
		JsonObject.addProperty("data", "Error to create user");
		try {
			User userObj = new Gson().fromJson(userObjStr, User.class);
			JsonObject registerStatus = userService.registerUser(userObj);
			return registerStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonObject.toString();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody String loginObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "Invalid Username/Password");
		jsonObject.addProperty("success", false);
		try {
			User user = new Gson().fromJson(loginObjStr, User.class);
			JsonObject loginStatus = userService.login(user);
			return loginStatus.toString();
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String logout(@RequestBody String logoutObjStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "logout unsuccessful");
		jsonObject.addProperty("success", false);
		try {
			UserSession userSession = new Gson().fromJson(logoutObjStr, UserSession.class);
			JsonObject logoutStatus = userService.logout(userSession);
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
	public String validate(@RequestBody String validateTokenStr) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("data", "invalid token");
		jsonObject.addProperty("success", false);
		try{
			UserSession userSession = new Gson().fromJson(validateTokenStr, UserSession.class);
			JsonObject validateStatus = userService.validate(userSession);
			return validateStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}
}