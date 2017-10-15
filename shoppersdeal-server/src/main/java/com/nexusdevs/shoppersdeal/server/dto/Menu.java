package com.nexusdevs.shoppersdeal.server.dto;

import java.util.List;

public class Menu {

	private Category category;
	private List<SubCategory> subcategory;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<SubCategory> getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(List<SubCategory> subcategory) {
		this.subcategory = subcategory;
	}
}