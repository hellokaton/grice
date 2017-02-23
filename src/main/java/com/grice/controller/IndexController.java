package com.grice.controller;

import com.blade.kit.CollectionKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.Controller;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.blade.mvc.view.ModelAndView;
import com.grice.init.Constant;
import com.grice.kit.MarkdownKit;
import com.grice.model.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.blade.Blade.$;

/**
 * 页面控制器
 */
@Controller
public class IndexController {

    /**
     * 首页
     *
     * @param mav
     * @return
     */
    @Route(value = {"/", "index"}, method = HttpMethod.GET)
    public ModelAndView index(ModelAndView mav) {
        mav.setView("home.html");
        return mav;
    }

    /**
     * 文档页
     *
     * @param mav
     * @param request
     * @param response
     * @return
     */
    @Route(value = "docs", method = HttpMethod.GET)
    public ModelAndView docs(ModelAndView mav, Request request, Response response) {
        List<Node> nodes = this.getNodes();

        Node first = nodes.get(0);

        String content = MarkdownKit.getNodeDoc(first.getPath() + "/README.md").getContent();
        String title = first.getTitle();
        // 没有内容
        if (StringKit.isBlank(content.trim())) {
            if (CollectionKit.isNotEmpty(first.getDocs())) {
                Node doc = first.getDocs().get(0);
                content = MarkdownKit.getNodeDoc(doc.getPath()).getContent();
                title = doc.getTitle();
            }
        }

        mav.add("title", title);
        mav.add("nodes", nodes);
        mav.add("content", content);
        mav.setView("docs.html");
        return mav;
    }

    /**
     * 节点页
     *
     * @param mav
     * @param request
     * @param node
     * @return
     */
    @Route(value = "docs/:node", method = HttpMethod.GET)
    public ModelAndView showRootDetail(ModelAndView mav, Request request, @PathParam("node") String node) {
        String target = $().config().get("grice.docs.target");
        String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
        String path = target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
        List<Node> nodes = this.getNodes();
        mav.add("nodes", nodes);

        Node doc = MarkdownKit.getNodeDoc(path);
        if (null != doc) {
            mav.add("title", doc.getTitle());
            mav.add("content", doc.getContent());
            mav.add("current", doc.getName());
            mav.setView("docs.html");
        } else {
            mav.setView("404.html");
        }
        return mav;
    }

    /**
     * 文档详情页
     *
     * @param mav
     * @param request
     * @param nodeName
     * @param docName
     * @return
     */
    @Route(value = "docs/:node/:doc", method = HttpMethod.GET)
    public ModelAndView showDetail(ModelAndView mav, Request request,
                                   @PathParam("node") String nodeName,
                                   @PathParam("doc") String docName) {

        String target = $().config().get("grice.docs.target");
        String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
        List<Node> nodes = this.getNodes();
        mav.add("nodes", nodes);

        String raw = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";

        Node doc = MarkdownKit.getNodeDoc(raw);
        if (null != doc) {
            mav.add("title", doc.getTitle());
            mav.add("content", doc.getContent());
            mav.add("current", doc.getName());
            mav.setView("docs.html");
        } else {
            mav.setView("404.html");
        }
        return mav;
    }

    private List<Node> getNodes() {

        String target = $().config().get("grice.docs.target");
        String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
        String path = target + File.separatorChar + lang;

        List<Node> nodes = CollectionKit.newArrayList(16);
        File dir = new File(path);
        File[] subDirs = dir.listFiles();
        if (null != subDirs) {
            nodes = new ArrayList<Node>(subDirs.length);
            for (File sub : subDirs) {
                if ('.' != sub.getName().charAt(0)) {
                    Node node = new Node(sub.getName().replace(".md", ""), sub.getPath());
                    nodes.add(node);
                }
            }
        }
        Collections.sort(nodes, Constant.comparator);
        return nodes;
    }

}
