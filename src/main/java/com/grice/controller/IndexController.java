package com.grice.controller;

import com.blade.annotation.Controller;
import com.blade.annotation.Route;
import com.blade.view.ModelAndView;
import com.blade.web.http.Request;
import com.blade.web.http.Response;

/**
 * Created by oushaku on 16/8/31.
 */
@Controller
public class IndexController {

    public ModelAndView index(Request request, Response response){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("/templates/home.html");
        return modelAndView;
    }

}
