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
@Service //서비스 선언!(서비스 객체를 스프링부트에 생성)
@Slf4j //log 사용할 수 있게
public class ArticleService {
    //ArticleService가 Repository와 협업 할 수 있게 필드를 선언해준다!
    @Autowired
    private ArticleRepository articleRepository;

    //재료다가져와~
    public List<Article> index() {

        return articleRepository.findAll(); //요리사가 repository를 통해 데이터(재료)를 가져오게함!
    }

    //단건조회(재료몇개가져와~)
    public Article show(Long id) { //보조요리사와 웨이터는 만날일이없어!!!!!!요리사가시키는거야!

        return  articleRepository.findById(id).orElse(null);
    }

    //주방장의 메서드
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){
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
        target.patch(article);///////////추가된코드
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
        articleRepository.delete(target);
        return target;
    }


    @Transactional //해당 메소드를 트랜잭션으로 묶는다! 트랜잭션은 서비스가 하는 행위이다
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // dto 묶음을 entity 묶음으로 변환
       List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());


        // entity묶음을  db로 저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제로 예의 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("결제 실패!")
        );

        //결과값 반환
        return null;
    }
}
