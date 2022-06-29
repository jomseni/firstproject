package com.example.firstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식가능!! 테이블로작성!
@AllArgsConstructor   //생성자를 대신한다.
@ToString
@NoArgsConstructor   //디폴트를 생성자를 추가!!!!!!!!
@Getter             //리다이렉트에서겟터가 필요한ㄷ ㅔ 그부분 말고 모든 게터를 사용하기위해 이걸로써줌
public class Article {

    @Id //대표값을 지정! like a 주민등록번호
    @GeneratedValue //1,2,3, ,...자동 생성 어노테이션!
    private Long id;    //entity에는 대표 값이 있어야한다!

    @Column     //DB에 이용할 것이므로 테이블에서 사용 되어지는 단위로 쓴다!
    private String title;

    @Column
    private String content;



    //여기까지가 entity 클래스를 선언한 것!

    //디폴트 생성자란? 파라메타가 아무것도 없는 생성자!!!!!



//    public Article(Long id, String title, String content) { //entity를 만들기위해 생성자를 추가한다.  @AllArgsConstructor으로 이부분을 대신한다. id,title,content를 가진 객체를 생성을 All 이자식이 다해준다.!
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }

//    @Override                 @ToString으로 대신한다!
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
