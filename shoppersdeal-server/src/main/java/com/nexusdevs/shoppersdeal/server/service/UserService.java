package com.nexusdevs.shoppersdeal.server.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.nexusdevs.shoppersdeal.server.dto.User;
import com.nexusdevs.shoppersdeal.server.dto.UserSession;
import com.nexusdevs.shoppersdeal.server.utils.JsonUtils;
import com.nexusdevs.shoppersdeal.server.utils.StringUtils;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private DAOService daoService;

	public JsonObject registerUser(User user) {
		try {
			User userStatus = daoService.getUserDetail(user.getEmail());
			if (userStatus != null)
				return JsonUtils.errorResponse("User Already Exists");
			
			user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setPasscode(StringUtils.getMD5Hash(user.getPasscode()));
			user.setCreateTime(System.currentTimeMillis());
			user.setUpdateTime(0);
			user.setVerified(false);
			user.setBlocked(false);
			
			String createUserStatus = daoService.createUser(user);
			return JsonUtils.successResponse(createUserStatus);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JsonObject login(User user) {
		try {
			User loginStatus = daoService.login(user.getEmail(), StringUtils.getMD5Hash(user.getPasscode()));
			if(loginStatus == null || loginStatus.equals(""))
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
	
	public JsonObject logout(UserSession userSession) {
		try {
			daoService.logout(userSession);
			return JsonUtils.successResponse("logout successful");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JsonObject validate(UserSession userSession) {
		try {
			UserSession validateStatus = daoService.validate(userSession);
			if(validateStatus == null || validateStatus.equals(""))
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
		daoService.insertSession(session);
		return session;
	}
}