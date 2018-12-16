package com.grice.bootstrap;

import com.grice.model.NodeComparator;

import java.util.HashMap;
import java.util.Map;

public interface Constant {

	Map<String, String> ALL_LANGS = new HashMap<>(2);
	Map<String, String> LOCALES = new HashMap<String, String>();
	NodeComparator comparator = new NodeComparator();
}
