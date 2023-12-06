package com.jjsetting.jpajsporacle.domain.article.article.service;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import com.jjsetting.jpajsporacle.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void write(String title,String content){
        articleRepository.save(
                Article.builder()
                        .title(title)
                        .content(content)
                        .build()
        );
    }
}
