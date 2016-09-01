package com.grice.interceptor;

import com.blade.annotation.Intercept;
import com.blade.interceptor.Interceptor;
import com.blade.web.http.Request;
import com.blade.web.http.Response;
import com.grice.config.Constant;

@Intercept
public class BaseInterceptor implements Interceptor {
	
	@Override
	public boolean before(Request request, Response response) {
		
		System.out.println("before");
		String lang = request.query("lang");
		// 切换语言
        if(null != lang && Constant.ALL_LANGS.containsKey(lang)){
        	Constant.VIEW_CONTEXT.set("Lang", lang);
        }
		request.attribute("lang", Constant.VIEW_CONTEXT.getValue("Lang").toString());
		return true;
	}

	@Override
	public boolean after(Request request, Response response) {
		return true;
	}

}
