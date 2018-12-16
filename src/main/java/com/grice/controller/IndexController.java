package com.grice.controller;

import com.blade.kit.CollectionKit;
import com.blade.kit.StringKit;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.ui.ModelAndView;
import com.grice.bootstrap.BootStrap;
import com.grice.util.GriceUtil;
import com.grice.model.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.grice.bootstrap.BootStrap.ENV;
import static com.grice.bootstrap.BootStrap.VIEW_CONTEXT;


/**
 * 页面控制器
 */
@Path
public class IndexController {

    /**
     * 首页
     */
    @Route(value = {"/", "index"}, method = HttpMethod.GET)
    public String index() {
        return "home.html";
    }

    /**
     * 文档页
     */
    @Route(value = "docs", method = HttpMethod.GET)
    public String docs(ModelAndView mav) {
        List<Node> nodes = this.getNodes();

        Node first = nodes.get(0);

        String content = GriceUtil.getNodeDoc(first.getPath() + "/README.md").getContent();

        String title = first.getTitle();
        // 没有内容
        if (StringKit.isBlank(content.trim())) {
            if (CollectionKit.isNotEmpty(first.getDocs())) {
                Node doc = first.getDocs().get(0);
                content = GriceUtil.getNodeDoc(doc.getPath()).getContent();
                title = doc.getTitle();
            }
        }

        mav.add("title", title);
        mav.add("nodes", nodes);
        mav.add("content", content);
        return "docs.html";
    }

    /**
     * 节点页
     */
    @Route(value = "docs/:node", method = HttpMethod.GET)
    public ModelAndView showRootDetail(ModelAndView mav, @PathParam String node) {
        String target = ENV.getOrNull("grice.docs.target");
        String lang   = VIEW_CONTEXT.getValue("Lang").toString();
        String path   = buildNodePath(node, target, lang);

        return getModelAndView(mav, path);
    }

    /**
     * 文档详情页
     */
    @GetRoute("docs/:nodeName/:docName")
    public ModelAndView showDetail(ModelAndView mav,
                                   @PathParam String nodeName,
                                   @PathParam String docName) {

        String target = ENV.getOrNull("grice.docs.target");
        String lang   = VIEW_CONTEXT.getValue("Lang").toString();
        String raw    = buildDetailPath(nodeName, docName, target, lang);

        return getModelAndView(mav, raw);
    }

    private ModelAndView getModelAndView(ModelAndView mav, String raw) {
        List<Node> nodes = this.getNodes();
        mav.add("nodes", nodes);

        Node doc = GriceUtil.getNodeDoc(raw);
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

    private String buildDetailPath(@PathParam String nodeName, @PathParam String docName, String target, String lang) {
        return target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName + ".md";
    }

    private List<Node> getNodes() {
        String target = ENV.getOrNull("grice.docs.target");
        String lang   = VIEW_CONTEXT.getValue("Lang").toString();
        String path   = target + File.separatorChar + lang;

        List<Node> nodes = new ArrayList<>(16);
        File       dir   = new File(path);

        if (null != dir.listFiles()) {
            nodes = new ArrayList<>();
            for (File sub : dir.listFiles()) {
                if ('.' != sub.getName().charAt(0)) {
                    Node node = new Node(sub.getName().replace(".md", ""), sub.getPath());
                    nodes.add(node);
                }
            }
        }
        nodes.sort(Comparator.comparingInt(Node::getSort));
        return nodes;
    }

    private String buildNodePath(@PathParam String node, String target, String lang) {
        return target + File.separatorChar + lang + File.separatorChar + node + File.separatorChar + "README.md";
    }

}
