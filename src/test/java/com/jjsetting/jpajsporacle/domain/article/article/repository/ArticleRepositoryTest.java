package com.jjsetting.jpajsporacle.domain.article.article.repository;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import com.jjsetting.jpajsporacle.domain.article.article.mapper.ArticleMapper;
import com.jjsetting.jpajsporacle.domain.article.article.mapper.ArticleMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @DisplayName("findFirstByOrderByIdDesc")
    @Test
    void findFirstByOrderByIdDesc(){
        Optional<Article> articleOp = articleRepository.findFirstByOrderByIdDesc();
        assertThat(articleOp.isPresent()).isTrue();
    }

    @DisplayName("findTop3ByOrderByIdDesc")
    @Test
    void findTop3ByOrderByIdDesc(){
        List<Article> articles =articleRepository.findTop3ByOrderByIdDesc();

        Article latestArticle = articleRepository.findFirstByOrderByIdDesc().get();

        long idLast = latestArticle.getId();
        long idSecondLast = idLast - 1;
        long idThirdLast = idLast-2;

        assertThat(articles)
                .hasSize(3)
                .extracting(Article::getId)
                .containsExactly(idLast,idSecondLast,idThirdLast);
    }

    @DisplayName("search")
    @Test
    void search(){
        int page=1;
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page-1,10,Sort.by(sorts));
        Page<Article> ariclePage = articleRepository.search(
                List.of("title","content"),
                "제목",
                pageable
        );
    }
}
