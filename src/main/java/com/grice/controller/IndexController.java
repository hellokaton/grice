package com.grice.controller;

import static com.blade.Blade.$;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.blade.annotation.Controller;
import com.blade.annotation.Route;
import com.blade.view.ModelAndView;
import com.blade.web.http.HttpMethod;
import com.blade.web.http.Request;
import com.blade.web.http.Response;
import com.grice.config.Constant;
import com.grice.kit.MarkdownKit;
import com.grice.model.Node;
import com.grice.model.Toc;

@Controller("/")
public class IndexController {
	
	@Route(value = {"/", "index"}, method = HttpMethod.GET)
    public ModelAndView index(ModelAndView mav){
		mav.setView("home.html");
        return mav;
    }
	
	@Route(value = "docs", method = HttpMethod.GET)
    public ModelAndView docs(ModelAndView mav, Request request, Response response){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		File dir = new File(target + File.separatorChar + lang);
		
		Toc toc = new Toc();
		toc.setName("toc");
		toc.setPath(dir.getPath());
		
		List<Node> nodes = new ArrayList<Node>();
		File[] subDirs = dir.listFiles();
		for(File sub : subDirs){
			Node node = new Node(sub.getPath());
			node.setName(sub.getName());
			nodes.add(node);
		}
		toc.setNodes(nodes);
		mav.add("title", nodes.get(0).getTitle());
		mav.add("toc", toc);
		mav.add("content", MarkdownKit.getContent(nodes.get(0).getPath() + "/README.md"));
		mav.setView("docs.html");
        return mav;
    }
	
}
