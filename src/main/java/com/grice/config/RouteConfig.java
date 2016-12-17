package com.grice.config;

import static com.blade.Blade.$;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.blade.annotation.Order;
import com.blade.config.BaseConfig;
import com.blade.config.Configuration;
import com.blade.ioc.annotation.Component;
import com.blade.kit.StringKit;
import com.grice.kit.PropKit;
import com.grice.model.Extension;
import com.grice.model.Item;
import com.grice.model.Navbar;

@Component
@Order(sort = 2)
public class RouteConfig implements BaseConfig{
	
	@Override
	public void config(Configuration configuration) {
		String version = $().config().get("grice.version");
		String site_name = $().config().get("grice.site.name");
		String site_desc = $().config().get("grice.site.desc");
		String site_url = $().config().get("grice.site.url");
		String i18n_langs = $().config().get("grice.i18n.langs");
		String lang_names = $().config().get("grice.i18n.names");
		String navbar_str = $().config().get("grice.navbar");
		
		Map<String, Object> siteMap = new HashMap<String, Object>();
		siteMap.put("name", site_name);
		siteMap.put("desc", site_desc);
		siteMap.put("url", site_url);
		
		Constant.VIEW_CONTEXT.set(Map.class, "site", siteMap);
		Constant.VIEW_CONTEXT.set(String.class, "version", version);
		
		// 加载i18n   home.title
		String[] langs = StringKit.split(i18n_langs, ",");
		String[] langNames = StringKit.split(lang_names, ",");
		
		for(int i=0,len=langs.length; i<len; i++){
			
			Constant.ALL_LANGS.put(langs[i], langNames[i]);
			
			Map<String, String> langMap = PropKit.load("locale/" + langs[i] + ".lang");
			Set<Entry<String, String>> entrySet = langMap.entrySet();
			for (Map.Entry<String, String> entry : entrySet) {
				Constant.LOCALES.put(langs[i] + "." + entry.getKey(), entry.getValue());
			}
		}
		
//		Locale defaultLocale = Locale.getDefault();
//		String localLang = defaultLocale.toString();
//		Constant.VIEW_CONTEXT.set(String.class, "Lang", localLang);
		Constant.VIEW_CONTEXT.set(String.class, "Lang", "zh_CN");
		Constant.VIEW_CONTEXT.set(Map.class, "all_langs", Constant.ALL_LANGS);
		
		String[] navs = StringKit.split(navbar_str, ",");
		
		Navbar navbar = new Navbar();
		for(String nav : navs){
			String icon = $().config().get(StringKit.trim("grice.navbar." + nav.trim() + ".icon")); 
			String locale = $().config().get("grice.navbar." + nav.trim() + ".locale"); 
			String link = $().config().get("grice.navbar." + nav.trim() + ".link"); 
			Boolean blank = $().config().getBoolean("grice.navbar." + nav.trim() + ".blank"); 
			blank = null == blank ? false : blank;
			Item item = new Item(link, icon, locale, blank);
			navbar.addItem(item);
		}
		Constant.VIEW_CONTEXT.set(Navbar.class, "navbar", navbar);
		
		Extension extension = new Extension();
		Boolean enableDuoShuo = $().config().getBoolean("grice.extension.enableDuoShuo");
		extension.setEnableDuoShuo(enableDuoShuo);
		String duoShuoShortName = $().config().get("grice.extension.duoShuoShortName");
		extension.setDuoShuoShortName(duoShuoShortName);
		Constant.VIEW_CONTEXT.set(Extension.class, "extension", extension);
		
		/**********
		 * 计算页面   * 
		 * *********/
		
	}

}
