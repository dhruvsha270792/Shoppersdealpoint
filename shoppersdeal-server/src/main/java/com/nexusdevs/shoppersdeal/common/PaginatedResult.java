package com.nexusdevs.shoppersdeal.common;

import org.json.simple.JSONArray;

public class PaginatedResult {
	private int total;
	private int n;
	private int pos;
	private JSONArray data;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

}
