package com.grice.controller;

import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.PathParam;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.HttpMethod;
import com.blade.mvc.http.Request;
import com.blade.mvc.ui.RestResponse;
import com.grice.bootstrap.BootStrap;
import com.grice.bootstrap.Constant;
import com.grice.kit.MarkdownKit;
import com.grice.model.Node;

import java.io.File;

/**
 * 文档API
 */
@Path(value = "api", suffix = ".json")
public class ApiController {

    /**
     * 读取文档节点内容
     *
     * @param request
     * @param node
     * @return
     */
    @Route(value = "docs/:node", method = HttpMethod.GET)
    @JSON
    public RestResponse<Node> rootDetail(Request request, @PathParam String node) {

        String target = $().config().get("grice.docs.target");
        String lang = Constant.VIEW_CONTEXT.getValue("Lang").toString();
        String path = target + File.separatorChar + lang + File.separatorChar + node.replace(".json", "") + File.separatorChar + "README.md";
        RestResponse<Node> restResponse = new RestResponse<Node>();
        Node doc = MarkdownKit.getNodeDoc(path);
        restResponse.setPayload(doc);


        return restResponse;
    }

    /**
     * 读取文档详情
     *
     * @param request
     * @param nodeName
     * @param docName
     * @return
     */
    @Route(value = "docs/:nodeName/:docName", method = HttpMethod.GET)
    @JSON
    public RestResponse<Node> docDetail(Request request,
                                        @PathParam String nodeName,
                                        @PathParam String docName) {

        String target = $().config().get("grice.docs.target");
        String lang = BootStrap.VIEW_CONTEXT.getValue("Lang").toString();
        String path = target + File.separatorChar + lang + File.separatorChar + nodeName + File.separatorChar + docName.replace(".json", "") + ".md";

        RestResponse<Node> restResponse = new RestResponse<Node>();
        Node doc = MarkdownKit.getNodeDoc(path);
        restResponse.setPayload(doc);
        return restResponse;
    }

}
