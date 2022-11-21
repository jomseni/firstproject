package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Service 부분은 모두 주방장의 일~
@Service //서비스 선언!(해당 클래스를 서비스로 인식하여 스프링부트에 객체를 생성)
@Slf4j //log 사용할 수 있게
public class ArticleService {
    //ArticleService가 Repository와 협업 할 수 있게 필드를 선언해준다!
    @Autowired
    private ArticleRepository articleRepository;

    //재료다가져와~
    public List<Article> index() {
        return articleRepository.findAll(); //요리사(service)가 (보조요리사에게 명령)repository를 통해 데이터(재료)를 가져오게함!
    }
    //단건조회(재료몇개가져와~)
    public Article show(Long id) { //보조요리사와 웨이터는 만날일이없어!!!!!!요리사가시키는거야!
        return  articleRepository.findById(id).orElse(null);
    }
    //주방장의 메서드
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){  //postmapping은 생성이지 수정이 아니다! 따라서 이런 조건을 써줘야 한다.
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        //1. 수정용 엔티티 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}",id, article.toString());

        // 2. 대상 엔티티 찾기

        Article target = articleRepository.findById(id).orElse(null);

        //3 . 잘못된 요청 처리(대상이 없거나, id가 다른경우)
        if(target == null || id != article.getId()){
            log.info("잘못된 요청! id : {}, article: {}",id,article.toString());
            return null;
        }

        //4: 업데이트 (원래있던거 그대로 사용하기) , 나머지만 수정
        target.patch(article);///////////추가된코드 ,target = 수정한 엔티티
        Article updated = articleRepository.save(target);//////변경된코드 (article-> target)
        return updated;
    }

    public Article delete(Long id) {  //주방장은 요리주문 취소 됐으니깐 DB찾아서 지워
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //잘못된 요청 처리
        if (target ==null){
            return null;
        }
        // 대상 삭제
        //요리사가 보조요리사한테 시키는 것이라고 이해!
        articleRepository.delete(target);
        return target;
    }


    @Transactional //해당 메소드를 트랜잭션으로 묶는다! 트랜잭션은 서비스가 하는 행위이다 ,해당메서드가 수행되다가 실패가되면 이전 상태로 롤백을 한다.!트랜잭션의 특성!
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
       List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // entity묶음을  db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제로 예외 발생 (id는 음수가 될수 없으므로 예외발생)
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        //결과값 반환
        return articleList;
    }
}
