package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {          //컨트롤러에서 form 데이터를 받아올 그릇이라고 할 수 있다. (객체 DTO로 받아옴) = DTO 클래스

    private Long id;  //수정하는 부분에서 id값이 추가 되었으므로 id 변수 선언을 추가한다.
    private String title;
    private String content;

//    public ArticleForm(String title, String content) {  @AllArgsConstructor 으로 대신하는  부분! 롬복을 통한 리팩토링
//        this.title = title;
//        this.content = content;
//    }

//    @Override                     @ToString 으로 대신한다 !
//    public String toString(){
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() { //Article 타입을 반환하기 원한다,  Entity인 Article 객체를 반환한다!
        return new Article(id, title, content);   //새롭게 Article을 만들어서 반환한다. 이 Article은 Entity 클래스! 파라미터를 가져온다 entity 에서! title과 content 는 DTO의 값을 가져온다
    }
}
