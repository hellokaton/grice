package com.grice;

import com.blade.Blade;
import com.blade.embedd.EmbedJettyServer;
import com.blade.route.RouteHandler;
import com.blade.view.ModelAndView;
import com.blade.view.template.JetbrickTemplateEngine;
import com.blade.web.http.Request;
import com.blade.web.http.Response;

/**
 * Hello world!
 */
public class Application {
    public static void main(String[] args) throws Exception {
        Blade.$().viewEngin(new JetbrickTemplateEngine());
        Blade.$().get("/", new RouteHandler() {
            @Override
            public void handle(Request request, Response response) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setView("home.html");
                response.render(modelAndView);
            }
        });
        Blade.$().start(EmbedJettyServer.class);
    }
}
