package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> { //page처리도 할 수 있고 Sorting도 할 수 있는 repository, 첫 번째 인수는 관리할 대상,두 번째 인수에는  PK의 타입
    //특정 게시글의 모든 댓글 조회(SQL작성)
    @Query(value = "SELECT * " +
            "FROM comment" +
            " WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);  //articleid를 조회했을때 comment 묶음을 반환 했으면 좋겠다~

    //특정 닉네임의 모든 댓글 조회(XML 작성)
    List<Comment> findByNickname(String nickname);  //nickname을 조회했을떄 그에 맞는 comment 묶음을 반환 했으면 좋겠다~
}
