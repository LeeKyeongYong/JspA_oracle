package com.jjsetting.jpajsporacle.domain.article.article.mapper;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import com.jjsetting.jpajsporacle.domain.article.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @DisplayName("findFirstByOrderByIdDesc")
    @Test
    void findFirstByOrderByIdDesc(){
        Optional<Article> articleOp = articleMapper.findFirstByOrderByIdDesc();
        assertThat(articleOp.isPresent()).isTrue();
    }

    @DisplayName("findTop3BOrderByIdDesc")
    @Test
    void findTop3BOrderByIdDesc(){
        List<Article> articles = articleMapper.findTop3ByOrderByIdDesc();
        Article latestArticle  = articleMapper.findFirstByOrderByIdDesc().get();

        long idLast = latestArticle.getId();
        long idSecondLast = idLast - 1;
        long idThirdLast = idLast - 2;

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

        Page<Article> articlePage = articleMapper.search(
                List.of("title","content"),
                "제목",
                pageable
        );
        assertThat(articlePage.getContent())
                .extracting(Article::getTitle)
                .containsSequence("제목 4","제목 3","제목 2","제목 1");
    }

    @DisplayName("search2")
    @Test
    void search2(){
        int page = 1;
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));
        Page<Article> articlePage = articleMapper.search(
                List.of("title", "content"),
                "제목",
                pageable
        );

        assertThat(articlePage.getContent())
                .extracting(Article::getTitle)
                .containsSubsequence("제목4","제목3","제목2","제목1");
    }
}
