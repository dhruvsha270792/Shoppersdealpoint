package com.nexusdevs.shoppersdeal.server.common;

import com.google.gson.JsonArray;

public class PaginatedResult {
	private int total;
	private int n;
	private int pos;
	private JsonArray data;

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

	public JsonArray getData() {
		return data;
	}

	public void setData(JsonArray data) {
		this.data = data;
	}

}
