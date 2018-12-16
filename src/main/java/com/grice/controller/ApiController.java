package com.grice.controller;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.ui.RestResponse;
import com.grice.model.Node;
import com.grice.util.GriceUtil;

import java.io.File;

import static com.grice.bootstrap.BootStrap.ENV;
import static com.grice.bootstrap.BootStrap.VIEW_CONTEXT;

/**
 * 文档API
 */
@Path(value = "api", restful = true, suffix = ".json")
public class ApiController {

    /**
     * 读取文档节点内容
     */
    @GetRoute("docs/:node")
    public RestResponse<Node> rootDetail(@PathParam String node) {

        String target = ENV.getOrNull("grice.docs.target");
        String lang   = VIEW_CONTEXT.getValue("Lang").toString();
        String path   = buildNodePath(node, target, lang);

        return RestResponse.ok(GriceUtil.getNodeDoc(path));
    }

    /**
     * 读取文档详情
     */
    @GetRoute("docs/:nodeName/:docName")
    public RestResponse<Node> docDetail(@PathParam String nodeName,
                                        @PathParam String docName) {

        String target = ENV.getOrNull("grice.docs.target");
        String lang   = VIEW_CONTEXT.getValue("Lang").toString();
        String path   = buildDocPath(nodeName, docName, target, lang);

        return RestResponse.ok(GriceUtil.getNodeDoc(path));
    }

    private String buildDocPath(@PathParam String nodeName, @PathParam String docName, String target, String lang) {
        return target + File.separatorChar + lang + File.separatorChar + nodeName +
                File.separatorChar + docName.replace(".json", "") + ".md";
    }

    private String buildNodePath(@PathParam String node, String target, String lang) {
        return target + File.separatorChar + lang + File.separatorChar +
                node.replace(".json", "") + File.separatorChar + "README.md";
    }

}
