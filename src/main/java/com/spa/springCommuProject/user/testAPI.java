package com.spa.springCommuProject.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testAPI {

    @GetMapping("/test")
    public String test(){
        return "home";
    }
}
