package com.nexusdevs.shoppersdeal.server.dto;

import java.util.List;

import com.nexusdevs.shoppersdeal.server.common.DealCategory;

public class Products {

	private String productId;
	private List<String> category;
	private List<String> subcategory;
	private String productName;
	private String compName;
	private Float price;
	private Float discPrice;
	private Float priceDiff;
	private String summary;
	private List<String> images;
	private List<String> tags;
	private DealCategory dealCategory;
	private long createTime;
	private long updateTime;
	private boolean deleted;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public List<String> getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(List<String> subcategory) {
		this.subcategory = subcategory;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDiscPrice() {
		return discPrice;
	}

	public void setDiscPrice(Float discPrice) {
		this.discPrice = discPrice;
	}

	public Float getPriceDiff() {
		return priceDiff;
	}

	public void setPriceDiff(Float priceDiff) {
		this.priceDiff = priceDiff;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public DealCategory getDealCategory() {
		return dealCategory;
	}

	public void setDealCategory(DealCategory dealCategory) {
		this.dealCategory = dealCategory;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}