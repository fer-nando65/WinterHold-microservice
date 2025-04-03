package com.winterhold.error_handler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {

    @GetMapping("503")
    public String unavailablePage(){
        return "error/503-page";
    }

    @GetMapping("404")
    public String notFound(){
        return "error/404-page";
    }
}
