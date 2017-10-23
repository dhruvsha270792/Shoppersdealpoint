package com.nexusdevs.shoppersdeal.server.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.ConsoleUser;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;
import com.nexusdevs.shoppersdeal.server.utils.StringUtils;

public class AdminService {
	
	private static Logger logger= LoggerFactory.getLogger(AdminService.class);
	
	@Autowired
	private DAOService daoService;
	
	public JsonObject registerConsoleUser(ConsoleUser user) {
		try {
			ConsoleUser userStatus = daoService.getConsoleUserDetail(user.getEmail());
			if (userStatus != null)
				return JsonUtils.errorResponse("User Already Exists");
			
			user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setHp(StringUtils.getMD5Hash(user.getHp()));
			user.setCreateTime(System.currentTimeMillis());
			user.setUpdateTime(0);
			
			String createUserStatus = daoService.createConsoleUser(user);
			return JsonUtils.successResponse(createUserStatus);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JsonObject loginConsoleUser(ConsoleUser user) {
		try {
			ConsoleUser loginStatus = daoService.loginConsoleUser(user.getEmail(), StringUtils.getMD5Hash(user.getHp()));
			if(loginStatus == null)
				return JsonUtils.errorResponse("Invalid Username/Password");
			
			UserSession userSession = createSessionForConsole(loginStatus.getEmail());
			
			JsonObject JsonObject = new JsonObject();
			JsonObject.addProperty("success", true);
			JsonObject.addProperty("token", userSession.getToken());
			JsonObject.addProperty("userId", loginStatus.getUserId());
			
			return JsonObject;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JsonObject logoutConsoleUser(UserSession userSession) {
		try {
			daoService.logoutConsoleUser(userSession);
			return JsonUtils.successResponse("logout successful");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JsonObject validateConsoleUser(UserSession userSession) {
		try {
			UserSession validateStatus = daoService.validateConsoleUser(userSession);
			if(validateStatus == null)
				return JsonUtils.errorResponse("not valid");
			
			return JsonUtils.successResponse(validateStatus.toString());
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	private UserSession createSessionForConsole(String email) {
		long n = System.nanoTime() * System.nanoTime();
		if (n <= 0) {
			n = -n;
		}
		int k = (int) (email.hashCode() * Math.random());
		if (k < 0)
			k = -k;
		String token = n + "" + k;
		UserSession session = new UserSession();
		session.setCreateTime(System.currentTimeMillis());
		session.setToken(token);
		session.setUserId(email);
		daoService.insertConsoleSession(session);
		return session;
	}
}