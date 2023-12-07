package com.jjsetting.jpajsporacle.domain.article.article.mapper;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface ArticleMapper {
    @Select("""
            select * from ARTICLE
            WHERE ID = ${id}
            """)
    public Optional<Article> findById(long id);
    default Article save(Article article){
        if(article.getId() == null){
            article.setCreateDate(LocalDateTime.now());
            article.setModifyDate(LocalDateTime.now());
            _insert(article);
        } else {
            article.setModifyDate(LocalDateTime.now());
        }
        return article;
    }
    @Insert("""
             INSERT INTO ARTICLE (ID,CREATE_DATE,MODIFY_DATE,TITLE_CONTENT)
             VALUES (ARTICLE_SEQ.NEXTVAL,#{createDate}, #{modifyDate}, #{title}, #{content})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "ID")
    long _insert(Article article);

    @Update("""
            UPDATE ARTICLE
            SET MODIFY_DATE = #{modifyDate}, TITLE=#{title}, CONTENT = #{content}
            WHERE ID = #{id}
            """)
    void _update(Article article);

    @Select("""
            select *
            from (
                select A.*,
                ROW_NUMBER() ORVER(ORDER BY A.ID DESC) RN
                FROM ARTICLE A
                ) A
                WHERE A.RN <= 1
            """)
    Optional<Article> findFirstByOrderByIdDesc();

    @Select("""
            select *
            from (
              select A.*,
              ROW_NUMBER() OVER(ORDER BY A.ID DESC) RN
             ) A
             WHERE A.RN <= 3
             ORDER BY A.RN ASC
            """)
    List<Article> findTop3ByOrderByIdDesc();
}
