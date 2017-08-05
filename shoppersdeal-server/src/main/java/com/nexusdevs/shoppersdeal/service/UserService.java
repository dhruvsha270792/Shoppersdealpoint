package com.nexusdevs.shoppersdeal.service;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexusdevs.shoppersdeal.dto.User;
import com.nexusdevs.shoppersdeal.utils.JsonUtils;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private DAOService daoService;
	
	public JSONObject registerUser(JSONObject regUserObj) {
		System.out.println(regUserObj.get("age"));
		User user = JsonUtils.fromJsonObjToUser(regUserObj);
		
		/*User userAlreadyStatus = daoService.getUserDetail(user.getEmail());
		if(userAlreadyStatus != null)
			return JsonUtils.errorResponse("User Already Exists");
		
		user.setId(null);
		user.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
		user.setCreateTime(System.currentTimeMillis());
		user.setUpdateTime(System.currentTimeMillis());
		
		User userRegisterStatus = daoService.createUser(user);*/
		
		return null;
	}
}
