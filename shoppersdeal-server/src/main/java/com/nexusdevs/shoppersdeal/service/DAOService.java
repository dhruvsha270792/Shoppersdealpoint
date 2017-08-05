package com.nexusdevs.shoppersdeal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexusdevs.shoppersdeal.db.MongoDBManager;
import com.nexusdevs.shoppersdeal.dto.User;

@Service
public class DAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private MongoDBManager mongoDBManager;
	
	public User createUser(User user) {
		return user;
	}
	
	public String getUserDetail(String emailId) {
		return toString();
	}
}
