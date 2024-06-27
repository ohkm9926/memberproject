package com.member.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    //기본요청메소드
    @GetMapping("/")
    public String index(){

        return "index"; //templates 폴더의 index.html을 찾아감
    }
}
