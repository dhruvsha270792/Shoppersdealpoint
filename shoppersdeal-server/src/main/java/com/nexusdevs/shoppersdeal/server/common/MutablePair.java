package com.nexusdevs.shoppersdeal.server.common;

public class MutablePair<K, V> {
	private K key;
	private V val;

	public MutablePair(K k, V v) {
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
}