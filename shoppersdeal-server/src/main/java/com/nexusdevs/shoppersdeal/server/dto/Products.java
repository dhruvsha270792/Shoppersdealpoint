package com.nexusdevs.shoppersdeal.server.dto;

import java.util.List;

import com.nexusdevs.shoppersdeal.server.common.DealCategory;

public class Products {

	private String id;
	private String productId;
	private String productName;
	private String compName;
	private Float price;
	private Float discPrice;
	private Float priceDiff;
	private String summary;
	private List<Articles> article;
	private List<Rating> rating;
	private Boolean availability;
	private Float quantity;
	private List<String> images;
	private List<String> productCategory;
	private List<String> productSubCategory;
	private List<String> tags;
	private DealCategory dealCategory;
	private long createTime;
	private long updateTime;
	private boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public List<Articles> getArticle() {
		return article;
	}

	public void setArticle(List<Articles> article) {
		this.article = article;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(List<String> productCategory) {
		this.productCategory = productCategory;
	}

	public List<String> getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(List<String> productSubCategory) {
		this.productSubCategory = productSubCategory;
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