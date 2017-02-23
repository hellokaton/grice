package com.grice.kit;

import com.blade.kit.CollectionKit;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public final class PropKit {

	private PropKit() {
	}

	public static Map<String, String> load(String path) {
		Properties props = new Properties();
		try {
			props.load(new InputStreamReader(PropKit.class.getClassLoader().getResourceAsStream(path), "UTF-8"));

            Map<String, String> map = new HashMap<>(props.size());
            Set<Entry<Object, Object>> set = props.entrySet();
			if (CollectionKit.isNotEmpty(set)) {
				Iterator<Map.Entry<Object, Object>> it = set.iterator();
				while (it.hasNext()) {
					Entry<Object, Object> entry = it.next();
					String key = entry.getKey().toString();
					String value = entry.getValue().toString().trim();
					String fuKey = getWildcard(value);
					if (null != fuKey && null != props.get(fuKey)) {
						String fuValue = props.get(fuKey).toString();
						value = value.replaceAll("\\$\\{" + fuKey + "\\}", fuValue);
					}
					map.put(key, value);
				}
			}
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getWildcard(String str) {
		if (null != str && str.indexOf("${") != -1) {
			int start = str.indexOf("${");
			int end = str.indexOf("}");
			if (start != -1 && end != -1) {
				return str.substring(start + 2, end);
			}
		}
		return null;
	}
}
