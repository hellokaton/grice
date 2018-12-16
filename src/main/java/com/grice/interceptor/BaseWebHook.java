package com.grice.interceptor;

import com.blade.ioc.annotation.Bean;
import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;
import com.grice.bootstrap.BootStrap;
import com.grice.bootstrap.Constant;

@Bean
public class BaseWebHook implements WebHook {

	@Override
	public boolean before(RouteContext context) {
		String lang = context.query("lang");
		// 切换语言
		if (null != lang && Constant.ALL_LANGS.containsKey(lang)) {
			BootStrap.VIEW_CONTEXT.set("Lang", lang);
			System.out.println(" => change language");
		}
		context.attribute("lang", BootStrap.VIEW_CONTEXT.getValue("Lang").toString());
		return true;
	}
}
