package com.apple.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;

@Controller
public class BasicController {
    @GetMapping("/")
    @ResponseBody
    String hello(){
        return "메인";
    }

    @GetMapping("/about")
    @ResponseBody
    String about(){
        return "피싱사이트에요.";
    }

    @GetMapping("/date")
    @ResponseBody
    String date(){
        var MsgDate = LocalDateTime.now();
        return String.valueOf(LocalDateTime.now());
    }
}