package com.jjsetting.jpajsporacle.domain.article.article.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@GetMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public String list(){
        return "domain/article/article/list";
    }
}
