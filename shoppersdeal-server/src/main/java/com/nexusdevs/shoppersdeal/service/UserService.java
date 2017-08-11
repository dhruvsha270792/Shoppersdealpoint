package com.nexusdevs.shoppersdeal.service;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexusdevs.shoppersdeal.dto.User;
import com.nexusdevs.shoppersdeal.utils.JsonUtils;
import com.nexusdevs.shoppersdeal.utils.StringUtils;

@Service
@SuppressWarnings({"unused"})
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private DAOService daoService;

	public JSONObject registerUser(JSONObject regUserObj) {
		try {
			User user = JsonUtils.fromJsonObjToUser(regUserObj);

			String userAlreadyStatus = daoService.getUserDetail(user.getEmail());

			if (userAlreadyStatus != null)
				return JsonUtils.errorResponse("User Already Exists");

			user.setId(null);
			user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setPasscode(StringUtils.getMD5Hash(user.getPasscode()));
			user.setCreateTime(System.currentTimeMillis());
			user.setUpdateTime(System.currentTimeMillis());

			User userRegisterStatus = daoService.createUser(user);
			return JsonUtils.successResponse(userRegisterStatus.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}

	public JSONObject login(JSONObject loginObj) {
		try {
			User user = JsonUtils.fromJsonObjToUser(loginObj);
			String passwordHash = StringUtils.getMD5Hash(user.getPasscode());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtils.errorResponse("error");
	}
}