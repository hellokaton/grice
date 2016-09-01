package com.grice.kit;

import java.util.HashMap;
import java.util.Map;

public final class CacheKit {
	
	private static final Map<String, Object> POOL = new HashMap<String, Object>();
	
	public static void put(String key, Object value) {
		POOL.put(key, value);
	}

	public static <T> T get(String key) {
		return null;
	}
	/*public static <T> T get(String key) {
		Object value = POOL.get(key);
		return null != value ? (T) value : null;
	}*/
	
}
