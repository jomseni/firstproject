package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //해당 클래스는 스프링부트와 연동되어 테스팅 된다!
class ArticleServiceTest {

    @Autowired ArticleService articleService;
    @Test
    void index() { //여러개를 조회할때! 리스트를 이용해서 테스트한다!
        // 예상
        Article a = new Article(1L,"가가가가","1111"); //Article객체를 생성한다.
        Article b = new Article(2L,"나나나나","2222");
        Article c = new Article(3L,"다다다다","3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));//a,b,c들을 List로 만들어준다!
        // 실제
        List<Article> articles= articleService.index(); //articleService가 index를 호출하면 리스트형태의 Article을 반환하고 그 이름을 articles 라고 지정한다.
        // 비교
        assertEquals(expected.toString(),articles.toString());
    }


    //조회 하는 부분!
    @Test
    void show_성공__존재하는_id_입력() { //객체 expected

        // 예상
        Long id = 1L;
        Article expected = new Article(id,"가가가가","1111");
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패__여러경우가_많지만_존재하지않는_id_입력했을경우() {
        // 예상
        Long id = -1L;
        Article expected = null; //show함수 정의할 때 id가 없는 값이면 null을 반환하게 했으므로 이렇게 작성한다!
        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected, article); //null은 toString 메서드를 호출 할 수 없으므로 지운다!
    }

    @Test
    @Transactional //데이터가 조회가 아닌 생성,삭제,수정 후엔 롤백을 해줘야한다!
    void create_성공__tite만__content만_있는_dto_입력() {
        // 예상
        String title="라라라라";
        String content="4444";
        ArticleForm dto = new ArticleForm(null,title,content);
        Article expected = new Article(4L,title,content); //4번 데이터가 나와야하므로 4번으로 설정!!!!
        // 실제
        Article article = articleService.create(dto);   //articleService가 create 메서드를 동작한다!
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패__id가_포함된_dto가_입력된__경우() { //create에서는 id가 포함되면 안된다!
        // 예상
        String title="라라라라";
        String content="4444";
        ArticleForm dto = new ArticleForm(4L,title,content);
        Article expected = null;
        // 실제
        Article article = articleService.create(dto);
        // 비교
        assertEquals(expected, article); //null값은 toString 메서드를 사용할 수 없다!.
        }

    @Test
    @Transactional
    void update_t_성공__존재하는_id와_title_content가_있는_dto_입력() {
        // 예상
        Long id = 1L;
        String title = "키키키키";
        String content = "1231231";
        ArticleForm dto = new ArticleForm(id,title,content);
        Article expected = new Article(id,title,content);

        // 실제
        Article article = articleService.update(id,dto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    //수정 : 내가 원하는 id 값에 title을 내가 원하는 것으로 수정하기!
    void update_t_성공__존재하는_id와_title만_있는_dto_입력() {
        // 예상
        Long id = 1L;
        String title = "히히히히";
        //content를 그대로 가져오기! 뭘까!
        Article content = articleService.show(id);

        ArticleForm dto = new ArticleForm(id, title, null);
        Article expected = new Article(id, title, "1111");   //원래 있던것의 content!

        // 실제
        Article article = articleService.update(id,dto);
        // 비교
        assertEquals(expected.toString(), article.toString());

    }


    @Test
    @Transactional
    void update_t_실패__존재하지_않는_id의_dto_입력() {
        // 예상
        Long id = -1L;   //존재하지 않는 id

        ArticleForm dto = new ArticleForm(id,null,null);

        Article expected = null;
        // 실제
        Article article = articleService.update(id,dto); //
        // 비교
        assertEquals(expected, article); //null은 toString 메서드를 호출 할 수 없으므로 지운다!


    }

    @Test
    @Transactional
    void delete_성공__존재하는__id입력() {
        // 예상
        Long id = 2L;
        Article expected = new Article(id,"나나나나","2222");

        // 실제
        Article article = articleService.delete(id); //

        // 비교
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void delete_실패__존재하지않는__id입력() {
        // 예상
        Long id = -1L;
        Article expected = null; //show함수 정의할 때 id가 없는 값이면 null을 반환하게 했으므로 이렇게 작성한다!
        // 실제
        Article article = articleService.delete(id);
        // 비교
        assertEquals(expected, article); //null은 toString 메서드를 호출 할 수 없으므로 지운다!
    }
}