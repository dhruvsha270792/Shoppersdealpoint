package com.nexusdevs.shoppersdeal.common;

public class Pair<K, V> {

	private K key;
	private V val;

	public Pair(K k, V v) {
		key = k;
		val = v;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getVal() {
		return val;
	}

	public void setVal(V val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "Pair [key=" + key + ", val=" + val + "]";
	}
}
