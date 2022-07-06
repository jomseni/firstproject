package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Comment {
    @Id  //id는 Id라고 생성해줌
    @GeneratedValue(strategy =  GenerationType.IDENTITY)   //DB가 자동으로 하나씩 증가 하게 생성하기 위한  어노테이션!
    private  Long id;



    //부모게시글에 대한 설정을 위한 어노테이션!
    @ManyToOne  //댓글입장에서 보기! 다대일 관계! 여러개의 댓글에 하나의 게시글에 달림, 해당 댓글 엔티티 여러개가, 하나의 Article에 연관된다!
    @JoinColumn(name = "article_id")  //테이블에서 연결된 대상정보를 가져와야하기 때문에 작성한다!,"artcleid" 컬럼에 Article의 대표값을 저장!
    //comment 테이블에서 joincolumn으로 컬럼명을 지정한다!
    //entity는! 테이블에서! 첫번째줄 목록! ex) ID BODY NICKNAME ARTICLE_ID가 Entity라고 할 수있다.
    private Article article;   //댓글의 부모 게시글

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null) //만약 dto의 id가 잘못 입력이 되었다면
            throw new IllegalArgumentException("댓글 생성 실패! 대상 게시글이 없습니다!");
        if(dto.getArticleId() != article.getId())   //id가 다를경우, dto.getArticleId 는 JSON(dto)에 쓰여지는 article id 값이다! dto는 json 데이터이다!
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다!");
        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(),article,dto.getNickname(),dto.getBody());

    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void patch(CommentDto dto) {   //Comment 엔티티에 patch 메서드를 정의한다!
        // 예외 발생
        if(this.id != dto.getId())  //url에서 던진 숫자의 id와 JSON에서 id 값의 숫자가 다르면~~ 예외발생
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        //객체를 갱신
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname(); //this.nickname 이란 원래 있던 데이터의 닉네임!

        if(dto.getBody() != null)
            this.body = dto.getBody();
    }
}
