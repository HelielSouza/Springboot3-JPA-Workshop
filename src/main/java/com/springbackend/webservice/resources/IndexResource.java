package com.springbackend.webservice.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/home")
public class IndexResource {
	
    @GetMapping
    public ModelAndView index() {
    	 ModelAndView mv = new ModelAndView();
    	 mv.setViewName("index");
    	 return mv;
    }
}
