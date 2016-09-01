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
import com.blade.view.ModelAndView;
import com.blade.web.http.HttpMethod;
import com.blade.web.http.Request;
import com.blade.web.http.Response;
import com.grice.config.Constant;
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
		List<Node> nodes = this.getNodes();
		mav.add("title", nodes.get(0).getTitle());
		mav.add("nodes", nodes);
		mav.add("content", MarkdownKit.getContent(nodes.get(0).getPath() + "/README.md").getContent());
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node", method = HttpMethod.POST)
	@JSON
    public RestResponse<Doc> rootDetail(Request request, @PathVariable("node") String node){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
		
		RestResponse<Doc> restResponse = new RestResponse<Doc>();
		Doc doc = MarkdownKit.getContent(path);
		restResponse.setPayload(doc);
        return restResponse;
    }
	
	@Route(value = "docs/:node", method = HttpMethod.GET)
    public ModelAndView showRootDetail(Request request, @PathVariable("node") String node){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
		
		List<Node> nodes = this.getNodes();
		
		ModelAndView mav = new ModelAndView();
		Doc doc = MarkdownKit.getContent(path);
		mav.add("title", doc.getTitle());
		mav.add("nodes", nodes);
		mav.add("content", doc.getContent());
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node/:doc_name", method = HttpMethod.GET)
    public ModelAndView showDetail(Request request, @PathVariable("node") String nodeName,@PathVariable("doc_name") String docName){
		
		ModelAndView mav = new ModelAndView();
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		
		List<Node> nodes = this.getNodes();
		
		String raw = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";
		
		Doc doc = MarkdownKit.getContent(raw);
		
		mav.add("title", doc.getTitle());
		mav.add("nodes", nodes);
		mav.add("content", doc.getContent());
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node/:doc_name", method = HttpMethod.POST)
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
	
	private List<Node> getNodes(){
		
		String target = $().environment().getString("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang;
		
		List<Node> nodes = new ArrayList<Node>();
		
		File dir = new File(path);
		File[] subDirs = dir.listFiles();
		nodes = new ArrayList<Node>(subDirs.length);
		for(File sub : subDirs){
			Node node = new Node(sub.getPath());
			node.setName(sub.getName());
			nodes.add(node);
		}
		Collections.sort(nodes, comparator);
		return nodes;
	}
	
}
