package com.tenco.blog.article;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 게시글 관련 데이터베이스 접근을 담당
 * 기본적인 CRUD 제공
 */
// @Repository 생략 가능 --> JpaRepository -> 선언 되어 있음
public interface ArticleJpaRepository extends JpaRepository<Article, Long> {

    // 게시글과 사용자 정보가 포함된 엔티를 만들어 주어야 한다. (게시글 리스트 용)
    @Query("SELECT a FROM Article a JOIN FETCH a.user u ORDER BY a.id DESC")
    Page<Article> findAllJoinUser(Pageable pageable);

    // 게시글 ID 로 한방에 유저 정보도 가져오기 - JOIN FETCH 사용하면 됨
    @Query("SELECT a FROM Article a JOIN FETCH a.user u WHERE a.id = :id")
    Optional<Article> findByIdJoinUser(@Param("id") Long id);

}
