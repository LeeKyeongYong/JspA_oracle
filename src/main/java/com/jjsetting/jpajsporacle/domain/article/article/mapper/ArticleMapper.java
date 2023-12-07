package com.jjsetting.jpajsporacle.domain.article.article.mapper;

import com.jjsetting.jpajsporacle.domain.article.article.entity.Article;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

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

    default Page<Article> search(List<String> kwTypes,String kw,Pageable pageable){
        String title = kwTypes.contains("title") ? kw:null;
        String content = kwTypes.contains("content") ? kw: null;
        List<Article> articles = _searchRows(title,content,pageable.getOffset(),pageable.getOffset()+pageable.getPageSize());
        return PageableExecutionUtils.getPage(articles,pageable,()->_searchCount(title,content));
    }

    @Select("""
            select count(*)
            from article A
            where 1!=1
            <if test='title != null'>
                OR LOWER(A.TITLE) LIKE '%' || LOWER(#{title}) || '%'
            </if>
            <if test='content != null'>
                or lower(A.CONTENT) like '%' || LOWER(#{content}) || '%'
            </if>
            """)
    long _searchCount(String title,String content);

    @Select("""
            <script>
                select *
                from (
                   select A.*,
                    DENSE_RANK() OVER(ORDER BY A.ID DESC) RN
                   FROM ARTICLE A
                   WHERE 1 != 1
                   <if test='title != null '>
                        OR LOWER(A.TITLE) LIKE '%' || LOWER(#{title}) || '%'
                   </if>
                   <if test ='content != null'>
                     OR LOWER(A.CONTENT) LIKE '%' || LOWER(#{content}) || '%'
                   </if>
                   ) A
                   WHERE <![CDATA[ A.RN <= #{offsetPlusLimit} ]]>
                   AND <![CDATA[ A.RN > #{offset} ]]>
                   ORDER BY A.RN
            </script>
            """)
    List<Article> _searchRows(String title,String content,long offset,long offsetPlusLimit);
}
