package com.nexusdevs.shoppersdeal.server.dto;

import java.util.List;

import com.nexusdevs.shoppersdeal.server.common.DealCategory;

public class Products {
	
	private String id;
	private String pId;
	private String p_name;
	private String comp_name;
	private Float price;
	private Float disc_price;
	private Float price_diff;
	private String summary;
	private List<Articles> article;
	private List<Rating> rating;
	private Boolean availability;
	private Float quantity;
	private List<String> images;
	private List<String> p_category;
	private List<String> p_subCategory;
	private List<String> tags;
	private DealCategory deal_category;
	private boolean deleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getComp_name() {
		return comp_name;
	}

	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDisc_price() {
		return disc_price;
	}

	public void setDisc_price(Float disc_price) {
		this.disc_price = disc_price;
	}

	public Float getPrice_diff() {
		return price_diff;
	}

	public void setPrice_diff(Float price_diff) {
		this.price_diff = price_diff;
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

	public List<String> getP_category() {
		return p_category;
	}

	public void setP_category(List<String> p_category) {
		this.p_category = p_category;
	}

	public List<String> getP_subCategory() {
		return p_subCategory;
	}

	public void setP_subCategory(List<String> p_subCategory) {
		this.p_subCategory = p_subCategory;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public DealCategory getDeal_category() {
		return deal_category;
	}

	public void setDeal_category(DealCategory deal_category) {
		this.deal_category = deal_category;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}	
}