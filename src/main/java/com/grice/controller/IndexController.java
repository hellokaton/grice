package com.grice.controller;

import static com.blade.Blade.$;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.blade.annotation.Controller;
import com.blade.annotation.JSON;
import com.blade.annotation.PathVariable;
import com.blade.annotation.Route;
import com.blade.kit.EncrypKit;
import com.blade.view.ModelAndView;
import com.blade.web.http.HttpMethod;
import com.blade.web.http.Request;
import com.blade.web.http.Response;
import com.grice.config.Constant;
import com.grice.kit.CacheKit;
import com.grice.kit.MarkdownKit;
import com.grice.kit.NodeComparator;
import com.grice.model.Doc;
import com.grice.model.Node;
import com.grice.model.RestResponse;

@Controller("/")
public class IndexController {
	
	private NodeComparator comparator = new NodeComparator();
	
	@Route(value = {"/", "index"}, method = HttpMethod.GET)
    public ModelAndView index(ModelAndView mav){
		mav.setView("home.html");
        return mav;
    }
	
	@Route(value = "docs", method = HttpMethod.GET)
    public ModelAndView docs(ModelAndView mav, Request request, Response response){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		
		String path = target + File.separatorChar + lang;
		String key = EncrypKit.md5(path);
		
		List<Node> nodes = CacheKit.get(key + "_nodes");
		String title = CacheKit.get(key + "_title");
		String content = CacheKit.get(key + "_content");
		
		if(null == nodes){
			File dir = new File(path);
			File[] subDirs = dir.listFiles();
			nodes = new ArrayList<Node>(subDirs.length);
			for(File sub : subDirs){
				Node node = new Node(sub.getPath());
				node.setName(sub.getName());
				nodes.add(node);
			}
			Collections.sort(nodes, comparator);
			title = nodes.get(0).getTitle();
			content = MarkdownKit.getContent(nodes.get(0).getPath() + "/README.md").getContent();
			CacheKit.put(key + "_nodes", nodes);
			CacheKit.put(key + "_title", title);
			CacheKit.put(key + "_content", content);
		}
		
		mav.add("title", title);
		mav.add("nodes", nodes);
		mav.add("content", content);
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:doc_name", method = HttpMethod.GET)
	@JSON
    public RestResponse<Doc> rootDetail(Request request, @PathVariable("doc_name") String docName){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + docName + ".md";
		
		RestResponse<Doc> restResponse = new RestResponse<Doc>();
		Doc doc = MarkdownKit.getContent(path);
		restResponse.setPayload(doc);
        return restResponse;
    }
	
	@Route(value = "docs/:node/:doc_name", method = HttpMethod.GET)
	@JSON
    public RestResponse<Doc> docDetail(Request request, @PathVariable("node") String nodeName,@PathVariable("doc_name") String docName){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";
		
		RestResponse<Doc> restResponse = new RestResponse<Doc>();
		Doc doc = MarkdownKit.getContent(path);
		restResponse.setPayload(doc);
        return restResponse;
    }
	
}
