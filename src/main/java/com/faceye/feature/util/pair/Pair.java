package com.faceye.feature.util.pair;

public class Pair<K, V> {

	private K key = null;
	private V value = null;
	
	public Pair(K key,V value){
		this.setKey(key);
		this.setValue(value);
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
