package com.nexusdevs.shoppersdeal.db;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@SuppressWarnings({"unused"})
public class MongoDBManager {

	private static final Logger logger = LoggerFactory.getLogger(MongoDBManager.class);

	private MongoClient mongoClient;
	private MongoDatabase mongoDB;

	public MongoDBManager(String host, String dbName) {
		init(host, dbName);
	}

	private void init(String host, String dbName) {
		try {
			MongoClientOptions mongoClientOptions = MongoClientOptions.builder()
					.connectionsPerHost(50)
					.connectTimeout(10000)
					.maxWaitTime(10000)
					.threadsAllowedToBlockForConnectionMultiplier(50)
					.build();
			mongoClient = new MongoClient(host, mongoClientOptions);
			mongoClient.setWriteConcern(WriteConcern.SAFE);
			mongoDB = mongoClient.getDatabase(dbName);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public String addObject(String collectionName, String jsonData) {
		MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
		Document doc = Document.parse(jsonData);
		System.out.println(doc);
		collection.insertOne(doc);
		return null;
	}
}