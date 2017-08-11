package com.nexusdevs.shoppersdeal.controller;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexusdevs.shoppersdeal.service.UserService;

@SuppressWarnings({"unchecked"})
@Controller
@RequestMapping("/u")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/d")
	@ResponseBody
	public String startHere() {
		return "Chal gaya...";
	}
	
	@RequestMapping("/register")
	public String registerUser(@RequestBody String userObjStr) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("data", "Error to create user");
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
	
	@RequestMapping("/login")
	public String login(@RequestBody String loginObjStr) {
		JSONObject jsonObject  = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("data", "Invalid Username/Password");
		try{
			JSONObject loginObj = (JSONObject) JSONValue.parse(loginObjStr);
			JSONObject loginStatus = userService.login(loginObj);
			return loginStatus.toString();
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return jsonObject.toString();
	}
}