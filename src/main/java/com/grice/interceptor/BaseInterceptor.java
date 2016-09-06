package com.grice.interceptor;

import com.blade.mvc.annotation.Intercept;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.interceptor.Interceptor;
import com.grice.config.Constant;

@Intercept
public class BaseInterceptor implements Interceptor {
	
	@Override
	public boolean before(Request request, Response response) {
		
		System.out.println("before ... ");
		
		String lang = request.query("lang");
		System.out.println("lang = " + lang);
		// 切换语言
        if(null != lang && Constant.ALL_LANGS.containsKey(lang)){
        	Constant.VIEW_CONTEXT.set("Lang", lang);
        	System.out.println(" => change language");
        }
		request.attribute("lang", Constant.VIEW_CONTEXT.getValue("Lang").toString());
		return true;
	}
	
	@Override
	public boolean after(Request request, Response response) {
		return true;
	}

}
