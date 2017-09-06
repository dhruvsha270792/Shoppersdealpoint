package com.nexusdevs.shoppersdeal.server.dto;

import com.nexusdevs.shoppersdeal.server.common.AddressType;

public class Address {

	private String strtAdd1;
	private String strtAdd2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String landmark;
	private long createdTime;
	private long updatedTime;
	private AddressType addType;
	private boolean deleted;

	public String getStrtAdd1() {
		return strtAdd1;
	}

	public void setStrtAdd1(String strtAdd1) {
		this.strtAdd1 = strtAdd1;
	}

	public String getStrtAdd2() {
		return strtAdd2;
	}

	public void setStrtAdd2(String strtAdd2) {
		this.strtAdd2 = strtAdd2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public AddressType getAddType() {
		return addType;
	}

	public void setAddType(AddressType addType) {
		this.addType = addType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}