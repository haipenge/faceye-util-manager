package com.faceye.feature.util.pair;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

public class Pairs<K, V> {
	private List<Pair<K, V>> pairs = null;

	public Pairs() {
		pairs = new ArrayList<Pair<K, V>>(0);
	}

	public Pairs<K, V> add(K key, V value) {
		Pair<K, V> pair = new Pair<K, V>(key, value);
		pairs.add(pair);
		return this;
	}

	public List<Pair<K, V>> getPairs() {
		return pairs;
	}

	public Pair<K, V> getPair(K key) {
		Pair<K, V> pair = null;
		if (CollectionUtils.isNotEmpty(pairs)) {
			for (Pair<K, V> p : pairs) {
				if (StringUtils.equals(p.getKey().toString(), key.toString())) {
					pair = p;
					break;
				}
			}
		}
		return pair;
	}
	
	public Pair<K,V> getPairByValue(V v){
		Pair<K, V> pair = null;
		if (CollectionUtils.isNotEmpty(pairs)) {
			for (Pair<K, V> p : pairs) {
				if (StringUtils.equals(p.getValue().toString(), v.toString())) {
					pair = p;
					break;
				}
			}
		}
		return pair;
	}
}
