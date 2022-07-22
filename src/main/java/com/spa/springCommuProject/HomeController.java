package com.spa.springCommuProject;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
public class HomeController {





    //public String home(){
    @GetMapping("/")
    public @ResponseBody String home(){
        return "home";
    }
}
