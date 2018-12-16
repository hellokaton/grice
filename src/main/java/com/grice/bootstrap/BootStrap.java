package com.grice.bootstrap;

import com.blade.Blade;
import com.blade.Environment;
import com.blade.ioc.annotation.Bean;
import com.blade.loader.BladeLoader;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import com.grice.kit.PropKit;
import com.grice.model.Extension;
import com.grice.model.Item;
import com.grice.model.Navbar;
import jetbrick.template.JetGlobalContext;
import jetbrick.template.resolver.GlobalResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Bean
public class BootStrap implements BladeLoader {

    public static JetGlobalContext VIEW_CONTEXT = null;

    @Override
    public void load(Blade blade) {
        // template
        JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
        GlobalResolver resolver = templateEngine.getGlobalResolver();
        resolver.registerFunctions(Functions.class);
        VIEW_CONTEXT = templateEngine.getGlobalContext();
        blade.templateEngine(templateEngine);

        // configuration
        Environment env = blade.environment();

        String version      = env.getOrNull("grice.version");
        String siteName     = env.getOrNull("grice.site.name");
        String siteDesc     = env.getOrNull("grice.site.desc");
        String siteURL      = env.getOrNull("grice.site.url");
        String i18nLANGs    = env.getOrNull("grice.i18n.langs");
        String i18nNames    = env.getOrNull("grice.i18n.names");
        String navbarString = env.getOrNull("grice.navbar");

        Map<String, Object> siteMap = new HashMap<String, Object>();
        siteMap.put("name", siteName);
        siteMap.put("desc", siteDesc);
        siteMap.put("url", siteURL);

        VIEW_CONTEXT.set(Map.class, "site", siteMap);
        VIEW_CONTEXT.set(String.class, "version", version);

        // 加载i18n  home.title
        String[] langs     = i18nLANGs.split(",");
        String[] langNames = i18nNames.split(",");

        for (int i = 0, len = langs.length; i < len; i++) {

            Constant.ALL_LANGS.put(langs[i], langNames[i]);

            Map<String, String>            langMap  = PropKit.load("locale/" + langs[i] + ".lang");
            Set<Map.Entry<String, String>> entrySet = langMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                Constant.LOCALES.put(langs[i] + "." + entry.getKey(), entry.getValue());
            }
        }

        VIEW_CONTEXT.set(String.class, "Lang", "zh_CN");
        VIEW_CONTEXT.set(Map.class, "all_langs", Constant.ALL_LANGS);

        String[] navs = navbarString.split(",");

        Navbar navbar = new Navbar();
        for (String nav : navs) {
            String  icon   = env.getOrNull("grice.navbar." + nav.trim() + ".icon");
            String  locale = env.getOrNull("grice.navbar." + nav.trim() + ".locale");
            String  link   = env.getOrNull("grice.navbar." + nav.trim() + ".link");
            Boolean blank  = env.getBoolean("grice.navbar." + nav.trim() + ".blank", false);
            Item    item   = new Item(link, icon, locale, blank);
            navbar.addItem(item);
        }
        VIEW_CONTEXT.set(Navbar.class, "navbar", navbar);

        Extension extension     = new Extension();
        Boolean   enableDuoShuo = env.getBooleanOrNull("grice.extension.enableDuoShuo");
        extension.setEnableDuoShuo(enableDuoShuo);
        String duoShuoShortName = env.getOrNull("grice.extension.duoShuoShortName");
        extension.setDuoShuoShortName(duoShuoShortName);
        VIEW_CONTEXT.set(Extension.class, "extension", extension);
    }

}
