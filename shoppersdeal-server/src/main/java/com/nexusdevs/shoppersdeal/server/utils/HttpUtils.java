package com.nexusdevs.shoppersdeal.server.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String postData(String url, byte[] data, String contentType) {
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);

			HttpEntity entity = new ByteArrayEntity(data);
			post.setEntity(entity);
			post.setHeader("Content-Type", contentType);
			post.setHeader("Character-Encoding", "UTF-8");
			CloseableHttpResponse httpResponse = client.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				StringBuilder sb = new StringBuilder(1024);
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append('\n');
				}
				return sb.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static String postData(String url, String data, String contentType) {
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			if (data == null) {
				data = "";
			}
			HttpEntity entity = new StringEntity(data, "UTF-8");
			post.setEntity(entity);
			post.setHeader("Content-Type", contentType);
			post.setHeader("Character-Encoding", "UTF-8");
			CloseableHttpResponse httpResponse = client.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				StringBuilder sb = new StringBuilder(1024);
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append('\n');
				}
				return sb.toString();
			} else {
				logger.error("got error " + httpResponse.getStatusLine().getStatusCode() + ":" + httpResponse.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}
	
	public static String postData(String url, String data, String contentType, Map<String, String> header) {
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			if (data == null) {
				data = "";
			}
			
			HttpEntity entity = new StringEntity(data, "UTF-8");
			post.setEntity(entity);
			post.setHeader("Content-Type", contentType);
			post.setHeader("Character-Encoding", "UTF-8");
			
			post.addHeader("name", header.get("name"));
			post.addHeader("userId", header.get("userId"));
			post.addHeader("username", header.get("username"));
			
			CloseableHttpResponse httpResponse = client.execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				StringBuilder sb = new StringBuilder(1024);
				while ((line = br.readLine()) != null) {
					sb.append(line);
					sb.append('\n');
				}
				return sb.toString();
			} else {
				logger.error("got error " + httpResponse.getStatusLine().getStatusCode() + ":" + httpResponse.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static String get(String url) {
		return get(url, null);
	}

	public static String get(String url, Map<String, String> headerMap) {
		try {
			InputStream inputStream = getDataStream(url, headerMap);
			if (inputStream == null) {
				return null;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			StringBuilder sb = new StringBuilder(1024);
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static InputStream getDataStream(String url, Map<String, String> headerMap) {
		CloseableHttpClient client = null;
		try {
			client = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			if (headerMap != null && headerMap.size() > 0) {
				for (Entry<String, String> en : headerMap.entrySet()) {
					get.setHeader(en.getKey(), en.getValue());
				}
			}
			CloseableHttpResponse httpResponse = client.execute(get);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream inputStream = httpEntity.getContent();
				return inputStream;
			}
		} catch (Exception e) {
			logger.error("error while fetching url " + url + " . error = " + e.getMessage(), e);
		}
		return null;
	}
}