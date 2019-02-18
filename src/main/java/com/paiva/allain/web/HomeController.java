package com.paiva.allain.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
public class HomeController {

    /**
     * Configuration for home view
     * @param model model object from spring framework
     * @return view name of home page
     * */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Model model) {
        return new ModelAndView("index.html");
    }
}
