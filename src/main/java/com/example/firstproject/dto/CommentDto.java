package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //모든 생성자를 자동완성
@Getter
@ToString
@NoArgsConstructor  //default 생성자도 자동완성해준다
public class CommentDto {  //comment를 담을 그릇
    private Long id;    //comment의 id
    @JsonProperty("article_id")   //Json에서 날라오는 형식을 아래줄과 매칭시켜준다! 모양이다를때사용
    private Long articleId; //comment가 포함된 게시글의 id
    private String nickname;    //comment에 닉네임을쓰기로했기때문에 사용 JSON에서 받아올 때 사용한다
    private String body;


    //static? 클래스 메서드에 선언할때 사용하는것이다!
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(  //생성자의 위에 파라미터들을 작성한다
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
