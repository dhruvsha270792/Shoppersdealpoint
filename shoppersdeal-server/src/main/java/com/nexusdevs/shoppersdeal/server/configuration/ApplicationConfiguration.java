package com.nexusdevs.shoppersdeal.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.nexusdevs.shoppersdeal.server.db.MongoDBManager;

@Configuration
@ComponentScan("com.nexusdevs.shoppersdeal")
@PropertySource("classpath:app.properties")
@EnableWebMvc
public class ApplicationConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	public MongoDBManager mongoDBManager(){
		String host = env.getProperty("mongo.shoppers.host");
		String db = env.getProperty("mongo.shoppers.db");
		MongoDBManager mdb = new MongoDBManager(host, db);
		return mdb;
	}
}