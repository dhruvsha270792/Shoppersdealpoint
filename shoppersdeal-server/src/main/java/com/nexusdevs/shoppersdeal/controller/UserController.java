package com.nexusdevs.shoppersdeal.controller;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nexusdevs.shoppersdeal.service.UserService;

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
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "Error to create user");
		jsonObject.put("success", false);
		try {
			JSONObject regUserObj = (JSONObject) JSONValue.parse(userObjStr);
			JSONObject registerStatus = userService.registerUser(regUserObj);
			return registerStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody String loginObjStr) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "Invalid Username/Password");
		jsonObject.put("success", false);
		try {
			JSONObject loginUserObj = (JSONObject) JSONValue.parse(loginObjStr);
			JSONObject loginStatus = userService.login(loginUserObj);
			return loginStatus.toString();
		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public String logout(@RequestBody String logoutObjStr) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "logout unsuccessful");
		jsonObject.put("success", false);
		try {
			JSONObject logoutUserObj = (JSONObject) JSONValue.parse(logoutObjStr);
			JSONObject logoutStatus = userService.logout(logoutUserObj);
			return logoutStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/validate", method = RequestMethod.POST)
	public String validate(@RequestBody String validateTokenStr) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("data", "invalid token");
		jsonObject.put("success", false);
		try{
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}
}