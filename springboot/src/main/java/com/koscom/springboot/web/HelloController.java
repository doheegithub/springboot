package com.koscom.springboot.web;

import com.koscom.springboot.web.dto.HelloResponseDto;
import org.hibernate.annotations.GeneratorType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("age") int amount){
        System.out.println("DTO =" + new HelloResponseDto(name, amount));
        return new HelloResponseDto(name, amount);
    }
}
