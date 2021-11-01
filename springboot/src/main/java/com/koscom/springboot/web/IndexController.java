package com.koscom.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/") //localhost::8080 쳤을때 나오는 장소
    public  String index(){
        return "index"; //template 밑에 있는 index.mustache를 자동으로 찾음
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }
}
