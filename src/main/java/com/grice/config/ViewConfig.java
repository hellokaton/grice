package com.grice.config;

import com.blade.annotation.Order;
import com.blade.config.ApplicationConfig;
import com.blade.config.BaseConfig;
import com.blade.ioc.annotation.Component;
import com.blade.mvc.view.ViewSettings;
import com.blade.mvc.view.template.JetbrickTemplateEngine;
import com.grice.ext.Funcs;

import jetbrick.template.resolver.GlobalResolver;

@Component
@Order(sort = 1)
public class ViewConfig implements BaseConfig{
	
	@Override
	public void config(ApplicationConfig applicationConfig) {
		
		System.out.println("1 ...");
		
		JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
		
		// 模板引擎
		GlobalResolver resolver = templateEngine.getJetEngine().getGlobalResolver();
		resolver.registerFunctions(Funcs.class);
		Constant.VIEW_CONTEXT = templateEngine.getJetEngine().getGlobalContext();
		
		ViewSettings.$().templateEngine(templateEngine);
	}

}
