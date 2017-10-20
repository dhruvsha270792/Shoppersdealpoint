package com.nexusdevs.shoppersdeal.server.configuration;

public class SolrConfiguration {
	private String writeBaseUrl;
	private String readBaseUrl;

	public String getWriteBaseUrl() {
		return writeBaseUrl;
	}

	public void setWriteBaseUrl(String writeBaseUrl) {
		this.writeBaseUrl = writeBaseUrl;
	}

	public String getReadBaseUrl() {
		return readBaseUrl;
	}

	public void setReadBaseUrl(String readBaseUrl) {
		this.readBaseUrl = readBaseUrl;
	}
}