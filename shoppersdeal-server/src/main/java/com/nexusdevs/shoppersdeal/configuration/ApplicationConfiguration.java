package com.nexusdevs.shoppersdeal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.nexusdevs.shoppersdeal.db.MongoDBManager;

@Configuration
@ComponentScan("com.nexusdevs.shoppersdeal")
@PropertySource("classpath:app.properties")
@EnableWebMvc
public class ApplicationConfiguration {

	@Autowired
	private Environment env;
	
	@Bean
	public MongoDBManager mongoDBManager(){
		String host = env.getProperty("mongo.deals.host");
		String db = env.getProperty("mongo.deals.db");
		MongoDBManager mdb = new MongoDBManager(host, db);
		return mdb;
	}
	
	/*@Bean
	public RedisManager redisManager() {
		String host = env.getProperty("redis.offers.host");
		RedisManager rm = new RedisManager(host);
		return rm;
	}*/
}