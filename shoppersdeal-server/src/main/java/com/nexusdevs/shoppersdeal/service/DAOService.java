package com.nexusdevs.shoppersdeal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexusdevs.shoppersdeal.db.MongoDBManager;

@Service
public class DAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private MongoDBManager mongoDBManager;
	
	public void registerUser() {
		
	}
}
