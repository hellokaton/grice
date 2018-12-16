package com.grice.bootstrap;

import static com.grice.bootstrap.BootStrap.VIEW_CONTEXT;

public class Functions {

    public static String lang(String name) {
        String lang = VIEW_CONTEXT.getValue("Lang").toString();
        String key  = lang + "." + name;
        return Constant.LOCALES.get(key);
    }

    public static String langName() {
        String lang = VIEW_CONTEXT.getValue("Lang").toString();
        return Constant.ALL_LANGS.get(lang);
    }

}