package com.grice.init;

import com.grice.kit.NodeComparator;
import jetbrick.template.JetGlobalContext;

import java.util.HashMap;
import java.util.Map;

public final class Constant {

	public static JetGlobalContext VIEW_CONTEXT = null;

	public static Map<String, Object> SITE_INFO = null;

	public static Map<String, String> ALL_LANGS = new HashMap<>(2);
	public static Map<String, String> LOCALES = new HashMap<String, String>();
	public static Map<String, String> DOC_LOCALES = new HashMap<String, String>();
	public static final NodeComparator comparator = new NodeComparator();
}
