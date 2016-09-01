package com.grice;

import static com.blade.Blade.$;

import com.blade.view.template.JetbrickTemplateEngine;
import com.grice.config.Constant;
import com.grice.ext.Funcs;

import jetbrick.template.resolver.GlobalResolver;

public class Application {
	
	public static void main(String[] args) throws Exception {
		JetbrickTemplateEngine templateEngine = new JetbrickTemplateEngine();
		
		// 模板引擎
		GlobalResolver resolver = templateEngine.getJetEngine().getGlobalResolver();
		resolver.registerFunctions(Funcs.class);
		Constant.VIEW_CONTEXT = templateEngine.getJetEngine().getGlobalContext();
		
		$().viewEngin(templateEngine);
		$().start(Application.class);
	}

}
