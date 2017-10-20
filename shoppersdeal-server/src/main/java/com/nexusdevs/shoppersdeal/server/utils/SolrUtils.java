package com.nexusdevs.shoppersdeal.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nexusdevs.shoppersdeal.server.dto.Products;

public class SolrUtils {
	
private static Logger logger = LoggerFactory.getLogger(SolrUtils.class);
	
	public static boolean addProductToSolr(Products product, String solrBaseUrl) {
		try {
			JsonArray arr = new JsonArray();
			String products = new Gson().toJson(product);
			
			JsonObject solrJSON = (new JsonParser()).parse(products).getAsJsonObject();
			solrJSON.addProperty("createTime", formatTime(product.getCreateTime()));
			solrJSON.addProperty("updateTime", formatTime(product.getUpdateTime()));
			arr.add(solrJSON);
			if(!solrBaseUrl.endsWith("/")) {
				solrBaseUrl += "/";
			}
			solrBaseUrl += "update?commit=true";
			String result = HttpUtils.postData(solrBaseUrl, arr.toString(), "application/json");
			logger.info("posted  to "+solrBaseUrl+" result = "+result);
			return result!=null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}
	
	/*public static boolean deleteVakFromSolr(String id, String solrBaseUrl) {
		try {
			if(id==null||id=="") {
				return true;
			}
			String deleteQuery = "<delete>";
			deleteQuery += "<query>id:"+id+"</query>";
			deleteQuery += "</delete>";
			if(!solrBaseUrl.endsWith("/")) {
				solrBaseUrl += "/";
			}
			solrBaseUrl += "update?commit=true";
			String result = HttpUtils.postData(solrBaseUrl, deleteQuery, "application/xml");
			logger.info("posted  to "+solrBaseUrl+" result = "+result);
			return result!=null;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return false;
	}*/
	
	public static String formatTime(long timeInMillis){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			String format = sdf.format(new Date(timeInMillis));
			return format;
		}catch(Exception e){
			
		}
		return null;
	}
	
	
	public static long formatTimeLong(String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			Date d = sdf.parse(dateFormat);
            long milliseconds = d.getTime();
		    return milliseconds;
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		return 0;
	}
}
