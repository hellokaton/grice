package com.blade.view.template;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;


import com.blade.context.ApplicationWebContext;
import com.blade.view.ModelAndView;
import com.blade.web.http.Request;
import com.blade.web.http.wrapper.Session;

import jetbrick.io.resource.ResourceNotFoundException;
import jetbrick.template.JetContext;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import jetbrick.template.TemplateException;
import jetbrick.template.web.JetWebEngine;

/**
 * JetbrickTemplateEngine
 * 
 * @author	<a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since	1.0
 */
public class JetbrickTemplateEngine implements TemplateEngine {

	private JetEngine jetEngine;

	public JetbrickTemplateEngine() {
		jetEngine = JetEngine.create();
	}
	
	public JetbrickTemplateEngine(ServletContext servletContext) {
		jetEngine = JetWebEngine.create(servletContext);
	}

	public JetbrickTemplateEngine(String conf) {
		jetEngine = JetEngine.create(conf);
	}

	public JetbrickTemplateEngine(JetEngine jetEngine) {
		if (null == jetEngine) {
			throw new IllegalArgumentException("jetEngine must not be null");
		}
		this.jetEngine = jetEngine;
	}

	public JetEngine getJetEngine() {
		return jetEngine;
	}

	@Override
	public void render(ModelAndView modelAndView, Writer writer) throws TemplateException {

		Map<String, Object> modelMap = modelAndView.getModel();
		modelMap = new HashMap<String, Object>();
		JetContext context = new JetContext(modelMap.size());

		Request request = ApplicationWebContext.request();
		Session session = request.session();

		Set<String> attrs = request.attributes();
		if (null != attrs && attrs.size() > 0) {
			for (String attr : attrs) {
				context.put(attr, request.attribute(attr));
			}
		}

		Set<String> session_attrs = session.attributes();
		if (null != session_attrs && session_attrs.size() > 0) {
			for (String attr : session_attrs) {
				context.put(attr, session.attribute(attr));
			}
		}

		context.putAll(modelMap);

		try {
			JetTemplate template = jetEngine.getTemplate(modelAndView.getView());
			template.render(context, writer);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
	}

}