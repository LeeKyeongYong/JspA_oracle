package com.jjsetting.jpajsporacle.domain.article.article.repository;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import com.jjsetting.jpajsporacle.domain.article.article.mapper.ArticleMapperTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
public class ArticleRepositoryTest {
    @Autowired
    private ArticleMapper articleMapper;

    @DisplayName("findFirstByOrderByIdDesc")
    @Test
    void findFirstByOrderByIdDesc(){
        Optional<Article> articleOp = articleMapper.findFirstByOrderByIdDesc();
        assertThat(articleOp.isPresent()).isTrue();
    }

    @DisplayName("findTop3ByOrderByIdDesc")
    @Test
    void findTop3ByOrderByIdDesc(){
        List<Article> articles =articleMapper.findTop3ByOrderByIdDesc();

        Article latestArticle = articleMapper.findFirstByOrderByIdDesc().get();

        long idLast = latestArticle.getId();
        long idSecondLast = idLast - 1;
        long idThirdLast = idLast-2;

        assertThat(articles)
                .hasSize(3)
                .extracting(Article::getId)
                .containsExactly(idLast,idSecondLast,idThirdLast);
    }
}
