package com.jjsetting.jpajsporacle.global.init;

import com.jjsetting.jpajsporacle.domain.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final ArticleService articleService;

    @Bean
    public ApplicationRunner initNotProd(){
        return args->{
          articleService.write("제목 1","내용 1");
          articleService.write("제목 2","내용 2");
          articleService.write("제목 3","내용 3");
        };
    }
}
