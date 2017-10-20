package com.nexusdevs.shoppersdeal.server.service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nexusdevs.shoppersdeal.server.common.SortOrder;
import com.nexusdevs.shoppersdeal.server.configuration.SolrConfiguration;
import com.nexusdevs.shoppersdeal.server.utils.HttpUtils;

public class SearchService {
	
	private static Logger logger = LoggerFactory.getLogger(SearchService.class);

	@Autowired
	private SolrConfiguration adSolrConfig;

	public List<String> searchShop(String query, Map<String, List<String>> filterparam, Map<String, SortOrder> sortMap, int num, int pos) {
		
		List<String> productIds = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String baseurl = adSolrConfig.getReadBaseUrl();
		if (!baseurl.endsWith("/")) {
			baseurl += "/";
		}
		sb.append(baseurl);
		sb.append("select");
		try {
			sb.append("?q=");
			if (query == null) {
				sb.append(URLDecoder.decode("*:*", "UTF-8"));
			}
			else {
				query = URLDecoder.decode(query, "UTF-8");
				sb.append(query.trim());
				sb.append("&defType=edismax");
				sb.append("&qf=");
				sb.append(URLEncoder.encode("productName^10 compName^20 tags^30", "UTF-8"));
			}
			if(sortMap!=null && sortMap.size()>0){
				String sortParam = sortMap.entrySet().stream().map(e -> e.getKey()+" "+(e.getValue()==SortOrder.ASCENDING?"asc":"desc")).collect(Collectors.joining(","));
				sb.append("&sort=");
				sb.append(URLEncoder.encode(sortParam, "utf-8"));
			}
			sb.append("&boost=");
			sb.append(URLEncoder.encode("recip(ms(NOW,createTime),2.78e-7,10,10)", "utf-8"));
			sb.append("&boost=likeCount");
			sb.append("&rows=");
			sb.append(num);
			sb.append("&start=");
			sb.append(pos);
			sb.append("&wt=json");
			sb.append("&fl=productId");
			
			String url = sb.toString();
			logger.debug("going to hit "+URLDecoder.decode(url, "UTF-8"));
			
			//System.out.println("going to hit "+url);
			
			String resp = HttpUtils.get(url);
			
			JsonObject resObj = (new JsonParser()).parse(resp).getAsJsonObject();
			JsonObject responseObj  =(JsonObject) resObj.get("response");
			JsonArray docArr = (JsonArray) responseObj.get("docs");
			for(int i=0;i<docArr.size();i++) {
				JsonObject shopObj = (JsonObject) docArr.get(i);
				String productId = shopObj.get("productId").getAsString();
				productIds.add(productId);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return productIds;
	}
	
	/*public List<Vak> profileFeed(String query, Map<String, List<String>> filterparam, Map<String, SortOrder> sortMap, int num, int pos) {
		List<Vak> vaks = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String baseurl = adSolrConfig.getReadBaseUrl();
		if (!baseurl.endsWith("/")) {
			baseurl += "/";
		}
		sb.append(baseurl);
		sb.append("select");
		try {
			sb.append("?q=username:");
			sb.append(URLEncoder.encode(query, "UTF-8").trim());
			sb.append("&defType=edismax");
			
			if(sortMap!=null && sortMap.size()>0) {
				String sortParam = sortMap.entrySet().stream().map(e -> e.getKey()+" "+(e.getValue()==SortOrder.ASCENDING?"asc":"desc")).collect(Collectors.joining(","));
				sb.append("&sort=");
				sb.append(URLEncoder.encode(sortParam, "UTF-8"));
			}
			sb.append("&boost=");
			sb.append(URLEncoder.encode("recip(ms(NOW,createTime),2.78e-7,10,10)", "utf-8"));
			sb.append("&boost=likeCount");
			sb.append("&start=");
			sb.append(pos);
			sb.append("&rows=");
			sb.append(num);
			sb.append("&wt=json");
			sb.append("&fl=");
			sb.append(URLDecoder.decode("*", "UTF-8"));
			
			String url = sb.toString();
			
			logger.debug("going to hit "+URLDecoder.decode(url, "UTF-8"));
			
			//System.out.println("going to hit "+url);
			
			String resp = HttpUtils.get(url);
			
			JsonObject resObj = (new JsonParser()).parse(resp).getAsJsonObject();
			JsonObject responseObj  =(JsonObject) resObj.get("response");
			JsonArray docArr = (JsonArray) responseObj.get("docs");
			
			for(int i=0; i<docArr.size(); i++) {
				JsonObject vakObj = (JsonObject) docArr.get(i);
				long createTime =  SolrUtils.formatTimeLong(vakObj.get("createTime").getAsString());
				long updateTime =  SolrUtils.formatTimeLong(vakObj.get("updateTime").getAsString());
				vakObj.addProperty("createTime", createTime);
				vakObj.addProperty("updateTime", updateTime);
				Vak vak = new Gson().fromJson(vakObj, Vak.class);
				vaks.add(vak);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return vaks;
	}
	
	public List<Vak> getVakFeed(List<String> query, Map<String, List<String>> filterparam, Map<String, SortOrder> sortMap, int num, int pos) {
		List<Vak> vaks = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String baseurl = adSolrConfig.getReadBaseUrl();
		if (!baseurl.endsWith("/")) {
			baseurl += "/";
		}
		sb.append(baseurl);
		sb.append("select");
		try {
			sb.append("?q=username:(");
			for(String followingName: query) {
				sb.append(URLEncoder.encode(followingName, "UTF-8").trim());
				sb.append(URLEncoder.encode(" ", "UTF-8"));
			}
			sb.append(")&defType=edismax");
			
			if(sortMap!=null && sortMap.size()>0) {
				String sortParam = sortMap.entrySet().stream().map(e -> e.getKey()+" "+(e.getValue()==SortOrder.ASCENDING?"asc":"desc")).collect(Collectors.joining(","));
				sb.append("&sort=");
				sb.append(URLEncoder.encode(sortParam, "UTF-8"));
			}
			sb.append("&boost=");
			sb.append(URLEncoder.encode("recip(ms(NOW,createTime),2.78e-7,10,10)", "utf-8"));
			sb.append("&boost=likeCount");
			sb.append("&start=");
			sb.append(pos);
			sb.append("&rows=");
			sb.append(num);
			sb.append("&wt=json");
			sb.append("&fl=");
			sb.append(URLDecoder.decode("*", "UTF-8"));
			
			String url = sb.toString();
			
			logger.debug("going to hit "+URLDecoder.decode(url, "UTF-8"));
			
			//System.out.println("going to hit "+url);
			
			String resp = HttpUtils.get(url);
			
			JsonObject resObj = (new JsonParser()).parse(resp).getAsJsonObject();
			JsonObject responseObj  =(JsonObject) resObj.get("response");
			JsonArray docArr = (JsonArray) responseObj.get("docs");
			
			for(int i=0; i<docArr.size(); i++) {
				JsonObject vakObj = (JsonObject) docArr.get(i);
				long createTime =  SolrUtils.formatTimeLong(vakObj.get("createTime").getAsString());
				long updateTime =  SolrUtils.formatTimeLong(vakObj.get("updateTime").getAsString());
				vakObj.addProperty("createTime", createTime);
				vakObj.addProperty("updateTime", updateTime);
				Vak vak = new Gson().fromJson(vakObj, Vak.class);
				vaks.add(vak);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return vaks;
	}
	
	
	public List<Vak> getPromotedVak(List<String> query, Map<String, List<String>> filterparam, Map<String, SortOrder> sortMap, int num, int pos) {
		List<Vak> vaks = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		String baseurl = adSolrConfig.getReadBaseUrl();
		if (!baseurl.endsWith("/")) {
			baseurl += "/";
		}
		sb.append(baseurl);
		sb.append("select");
		try {
			sb.append("?q=*:*");
			sb.append("&start=");
			sb.append(pos);
			sb.append("&rows=");
			sb.append(num);
			sb.append("&wt=json");
			sb.append("&fl=");
			sb.append(URLDecoder.decode("*", "UTF-8"));
			
			String url = sb.toString();
			
			logger.debug("going to hit "+URLDecoder.decode(url, "UTF-8"));
			
			//System.out.println("going to hit "+url);
			
			String resp = HttpUtils.get(url);
			//System.out.println(resp);
			
			JsonObject resObj = (new JsonParser()).parse(resp).getAsJsonObject();
			JsonObject responseObj  =(JsonObject) resObj.get("response");
			JsonArray docArr = (JsonArray) responseObj.get("docs");
			
			for(int i=0; i<docArr.size(); i++) {
				JsonObject vakObj = (JsonObject) docArr.get(i);
				long createTime =  SolrUtils.formatTimeLong(vakObj.get("createTime").getAsString());
				long updateTime =  SolrUtils.formatTimeLong(vakObj.get("updateTime").getAsString());
				vakObj.addProperty("createTime", createTime);
				vakObj.addProperty("updateTime", updateTime);
				Vak vak = new Gson().fromJson(vakObj, Vak.class);
				vaks.add(vak);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return vaks;
	}*/
}
