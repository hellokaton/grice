package com.grice.config;

import java.util.HashMap;
import java.util.Map;

import jetbrick.template.JetGlobalContext;

public final class Constant {
	
	public static JetGlobalContext VIEW_CONTEXT = null;
	
	public static Map<String, Object> SITE_INFO = null;
	
	public static Map<String, String> ALL_LANGS = new HashMap<String, String>(10);
	public static Map<String, String> LOCALES = new HashMap<String, String>();
	public static Map<String, String> DOC_LOCALES = new HashMap<String, String>();
	
}
