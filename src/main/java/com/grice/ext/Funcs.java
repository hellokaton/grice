package com.grice.ext;

import com.grice.init.Constant;

public class Funcs {

	public static String lang(String name) {
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String key = lang + "." + name;
		return Constant.LOCALES.get(key);
	}

	public static String langName() {
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		return Constant.ALL_LANGS.get(lang);
	}

}
