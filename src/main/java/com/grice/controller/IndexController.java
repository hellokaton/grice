package com.grice.controller;

import static com.blade.Blade.$;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.blade.kit.CollectionKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.PathVariable;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.view.ModelAndView;
import com.grice.Grice;
import com.grice.config.Constant;
import com.grice.kit.MarkdownKit;
import com.grice.model.Doc;
import com.grice.model.Node;
import com.grice.model.RestResponse;

@Controller
public class IndexController {
	
	@Route(value = {"/", "index"}, method = HttpMethod.GET)
    public ModelAndView index(ModelAndView mav){
		mav.setView("home.html");
        return mav;
    }
	
	@Route(value = "docs", method = HttpMethod.GET)
    public ModelAndView docs(ModelAndView mav, Request request, Response response){
		List<Node> nodes = this.getNodes();
		
		Node first = nodes.get(0);
		
		String content = MarkdownKit.getDoc(first.getPath() + "/README.md").getContent();
		String title = first.getTitle();
		// 没有内容
		if(StringKit.isBlank(content.trim())){
			if(CollectionKit.isNotEmpty(first.getDocs())){
				Node doc = first.getDocs().get(0);
				content = MarkdownKit.getDoc(doc.getPath()).getContent();
				title = doc.getTitle();
			}
		}
		
		mav.add("title", title);
		mav.add("nodes", nodes);
		mav.add("content", content);
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node", method = HttpMethod.POST)
	@JSON
    public RestResponse<Doc> rootDetail(Request request, @PathVariable("node") String node){
		
		String target = $().config().get("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
		
		RestResponse<Doc> restResponse = new RestResponse<Doc>();
		Doc doc = MarkdownKit.getDoc(path);
		restResponse.setPayload(doc);
        return restResponse;
    }
	
	@Route(value = "docs/:node", method = HttpMethod.GET)
    public ModelAndView showRootDetail(Request request, @PathVariable("node") String node){
		
		String target = $().config().get("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
		
		List<Node> nodes = this.getNodes();
		
		ModelAndView mav = new ModelAndView();
		Doc doc = MarkdownKit.getDoc(path);
		mav.add("title", doc.getTitle());
		mav.add("nodes", nodes);
		mav.add("content", doc.getContent());
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node/:doc_name", method = HttpMethod.GET)
    public ModelAndView showDetail(Request request, @PathVariable("node") String nodeName,@PathVariable("doc_name") String docName){
		
		ModelAndView mav = new ModelAndView();
		
		String target = $().config().get("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		
		List<Node> nodes = this.getNodes();
		
		String raw = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";
		
		Doc doc = MarkdownKit.getDoc(raw);
		
		mav.add("title", doc.getTitle());
		mav.add("nodes", nodes);
		mav.add("content", doc.getContent());
		
		mav.setView("docs.html");
        return mav;
    }
	
	@Route(value = "docs/:node/:doc_name", method = HttpMethod.POST)
	@JSON
    public RestResponse<Doc> docDetail(Request request, @PathVariable("node") String nodeName,@PathVariable("doc_name") String docName){
		
		String target = $().config().get("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";
		
		RestResponse<Doc> restResponse = new RestResponse<Doc>();
		Doc doc = MarkdownKit.getDoc(path);
		restResponse.setPayload(doc);
        return restResponse;
    }
	
	private List<Node> getNodes(){
		
		String target = $().config().get("grice.docs.target");
		String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
		String path = target + File.separatorChar + lang;
		
		List<Node> nodes = new ArrayList<Node>();
		
		File dir = new File(path);
		File[] subDirs = dir.listFiles();
		nodes = new ArrayList<Node>(subDirs.length);
		for(File sub : subDirs){
			if('.' != sub.getName().charAt(0)){
				Node node = new Node(sub.getPath());
				node.setName(sub.getName());
				nodes.add(node);
			}
		}
		Collections.sort(nodes, Grice.comparator);
		return nodes;
	}
	
}
