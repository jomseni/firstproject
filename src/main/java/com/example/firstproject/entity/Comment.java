package com.example.firstproject.entity;

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

    public void setArticle(Article article) {
        this.article = article;
    }
}
