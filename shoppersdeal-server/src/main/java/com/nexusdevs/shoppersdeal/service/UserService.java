package com.nexusdevs.shoppersdeal.service;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexusdevs.shoppersdeal.dto.User;
import com.nexusdevs.shoppersdeal.dto.UserSession;
import com.nexusdevs.shoppersdeal.utils.JsonUtils;
import com.nexusdevs.shoppersdeal.utils.StringUtils;

@Service
@SuppressWarnings({"unchecked"})
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private DAOService daoService;

	public JSONObject registerUser(JSONObject regUserObj) {
		try {
			User user = JsonUtils.fromJsonToUser(regUserObj);
			User userStatus = daoService.getUserDetail(user.getEmail());
			if (userStatus != null)
				return JsonUtils.errorResponse("User Already Exists");
			
			user.setId(null);
			user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setPasscode(StringUtils.getMD5Hash(user.getPasscode()));
			user.setCreateTime(System.currentTimeMillis());
			user.setUpdateTime(0);
			user.setVerified(false);
			user.setBlocked(false);
			
			daoService.createUser(user);
			return JsonUtils.successResponse("User Created");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JSONObject login(JSONObject loginUserObj) {
		try {
			User user = JsonUtils.fromJsonToUser(loginUserObj);
			User loginStatus = daoService.login(user.getEmail(), StringUtils.getMD5Hash(user.getPasscode()));
			
			UserSession userSession = createSessionForConsole(loginStatus.getEmail());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", true);
			jsonObject.put("token", userSession.getToken());
			jsonObject.put("userId", loginStatus.getUserId());
			
			return jsonObject;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
	
	public JSONObject logout(JSONObject logoutUserObj) {
		try {
			UserSession userSession = JsonUtils.fromJsonToUserSession(logoutUserObj);
			daoService.logout(userSession);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", true);
			jsonObject.put("data", "logout successful");
			return jsonObject;
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